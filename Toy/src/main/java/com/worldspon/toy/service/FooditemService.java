package com.worldspon.toy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.worldspon.toy.dto.foodimgfile.FoodimgfileRequestDto;
import com.worldspon.toy.dto.foodimgfile.FoodimgfileResponseDto;
import com.worldspon.toy.dto.fooditem.FooditemRequestArrayDto;
import com.worldspon.toy.dto.fooditem.FooditemRequestDto;
import com.worldspon.toy.dto.fooditem.FooditemResponseDto;
import com.worldspon.toy.entity.Foodimgfile;
import com.worldspon.toy.entity.Fooditem;
import com.worldspon.toy.repository.FoodimgfileRepository;
import com.worldspon.toy.repository.FooditemRepository;
import com.worldspon.toy.utils.FileUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FooditemService {
	
	private static Logger logger = LoggerFactory.getLogger(FooditemService.class);

	/**
	 * JPA repository 주입
	 * lombok 라이브러리의 AllArgsConstructor 어노테이션으로 생성자 자동 처리
	 */
	private FooditemRepository fooditemRepo;
	private FoodimgfileRepository foodimgfileRepo;
	// 이미지 파일 처리를 위한 커스텀 FileUtils 객체 주입
	private FileUtils fileUtils;
	
	
	/**
	 * 판매 중인 음식 메뉴 목록 조회 처리 서비스
	 * return data ------------------------
	 * foodlist				| 음식 메뉴 리스트 정보
	 * ------------------------------------
	 */
	@Transactional(readOnly = true)
	public ArrayList<FooditemResponseDto> listFooditem() throws Exception {
		// 판매 중인 음식 메뉴를 엔티티 클래스로 받아옴
		List<Fooditem> entityList = fooditemRepo.findByStatusAndStockGreaterThanEqual(1, 1);
		
		ArrayList<FooditemResponseDto> foodList = new ArrayList<FooditemResponseDto>();

		Iterator<Fooditem> entityIter = entityList.iterator();

		// 엔티티 클래스의 정보들을 응답 객체 전용 dto로 복사함
		while (entityIter.hasNext())
		{
			FooditemResponseDto dto = new FooditemResponseDto();
			BeanUtils.copyProperties(entityIter.next(), dto);
			
			foodList.add(dto);
		}
		
		return foodList;
	}
	
	/**
	 * 모든 음식 메뉴 목록 조회 처리 서비스
	 * return data ------------------------
	 * foodlist				| 모든 음식 메뉴 리스트 정보
	 * ------------------------------------
	 */
	@Transactional(readOnly = true)
	public ArrayList<FooditemResponseDto> listAllFooditem() throws Exception {
		List<Fooditem> entityList = fooditemRepo.findAll();
		
		ArrayList<FooditemResponseDto> foodList = new ArrayList<FooditemResponseDto>();
		
		for (int i = 0; i < entityList.size(); i += 1)
		{
			FooditemResponseDto dto = new FooditemResponseDto();
			BeanUtils.copyProperties(entityList.get(i), dto);
			
			foodList.add(dto);
		}
		
		return foodList;
	}
	
	/**
	 * 특정 음식 메뉴 조회 처리 서비스
	 * return data ------------------------
	 * foodlist				| 모든 음식 메뉴 리스트 정보
	 * ------------------------------------
	 */
	@Transactional(readOnly = true)
	public HashMap<String, Object> getFooditem(Long fid) {
		HashMap<String, Object> dtoMap = new HashMap<String, Object>();

		try 
		{
			Fooditem foodItemEntity = fooditemRepo.getOne(fid); // 음식 정보 조회 - 부모 테이블
			try
			{
				Foodimgfile foodImgFileEntity = foodimgfileRepo.findByFooditem_fid(fid); // 음식 이미지 파일 정보 조회 - 자식 테이블
				
				FooditemResponseDto foodItemDto = new FooditemResponseDto();
				BeanUtils.copyProperties(foodItemEntity, foodItemDto);
				foodItemDto.setFoodimgfile(foodImgFileEntity);
				
				dtoMap.put("fooditem", foodItemDto);
			}
			catch (IllegalArgumentException e) 
			{
				e.printStackTrace();
				// 주어진 ID 값이 NULL인 경우
				dtoMap.put("msg", "존재하지 않는 상품입니다.");
			}
		} 
		catch (EntityNotFoundException e) 
		{
			e.printStackTrace();
			// 주어진 ID값에 엔티티가 없을 경우
			dtoMap.put("msg", "존재하지 않는 테이블의 정보입니다.");
		}
		
		return dtoMap;
	}
	
	
	
	
	/**
	 * 음식 메뉴 등록 처리 서비스
	 * args -------------------------------
	 * dtolist			| 음식 메뉴 정보 객체 리스트
	 * req				| 요청 객체 정보 (Multipart로 캐스팅해서 사용해야함)
	 * return data ------------------------
	 * msg				| 음식 메뉴 등록 처리 결과 정보
	 * ------------------------------------
	 */
	@Transactional
	public String addFooditem(FooditemRequestArrayDto dtolist, HttpServletRequest req) {
		// 음식 메뉴 객체 리스트 정보
		Iterator<FooditemRequestDto> iter = dtolist.getFoodlist().iterator();
		
		// 음식 이미지 파일 객체 리스트 정보
		MultipartHttpServletRequest multipartReq = (MultipartHttpServletRequest)req;
		Iterator<String> fileIter = multipartReq.getFileNames();
		ArrayList<String> listFileNames = new ArrayList<String>();
		
		String msg = "";
		
		while (iter.hasNext())
		{
			FooditemRequestDto dto = iter.next();
			
			// 부모 테이블 insert 수행
			try 
			{
				Long fid = fooditemRepo.save(dto.toEntity()).getFid();
				dto.setFid(fid);	// 자식 테이블에 부모 pk를 저장하기 위해 setter로 값을 초기화
				// 이미지 파일들 이름 구하기
				MultipartFile multipartFile = multipartReq.getFile(fileIter.next());
				
				try 
				{
					HashMap<String, Object> fileNameMap = fileUtils.getFileName(multipartFile, dto.getFoodname());
					if (fileNameMap.isEmpty())
					{
						msg = "상품 등록 중 문제가 발생하였습니다.";
					}
					else
					{
						listFileNames.add(fileNameMap.get("imgFileName").toString());
						
						// 음식 메뉴 파일 정보 복사
						FoodimgfileRequestDto imgfiledto = new FoodimgfileRequestDto();
						imgfiledto.setImgfilename(fileNameMap.get("imgFileName").toString());
						imgfiledto.setOrgfilename(fileNameMap.get("orgFileName").toString());
						imgfiledto.setFooditem(dto.toEntity());
						
						// 자식 테이블 insert 수행
						foodimgfileRepo.save(imgfiledto.toEntity());
						
						// 이미지 파일을 생성함
						HashMap<String, Object> fileCreateMap = fileUtils.parseInsertFile(multipartFile, imgfiledto.getImgfilename());
						// fileException | -2: NullPointer, -1: IllegalState, 0: IO, 1: Success
						int fileException = Integer.valueOf(fileCreateMap.get("fileException").toString()); 
						
						// 에러가 발생한 경우
						if (fileException <= 0)
						{
							msg = fileCreateMap.get("msg").toString();
						}
						else
						{
							msg = "상품이 등록되었습니다.";
						}
					}
				} 
				catch (Exception e) 
				{
					msg = "상품 등록 중 문제가 발생하였습니다.";
				}
			} 
			catch (NumberFormatException e) 
			{
				msg = "잘못된 데이터 형식이 등록되었습니다.";
			}
		}
		
		return msg;
	}
	
	/**
	 * 음식 메뉴 수정 처리 서비스
	 * args -------------------------------
	 * fooditemDto		| 음식 메뉴 정보 객체
	 * req				| 요청 객체 정보 (Multipart로 캐스팅해서 사용해야함)
	 * return data ------------------------
	 * returnMsg		| 음식 메뉴 등록 처리 결과 정보
	 * ------------------------------------
	 */
	@Transactional
	public String modifyFooditem(FooditemRequestDto fooditemDto, HttpServletRequest req) {
		Long fid = fooditemDto.getFid();
		String returnMsg = "수정할 항목이 존재하지 않습니다.";
		
		// 변경할 이미지 파일 정보 구하기
		MultipartHttpServletRequest multipartReq = (MultipartHttpServletRequest)req;
		Iterator<String> multipartIter = multipartReq.getFileNames();
		MultipartFile multipartFile = null;
		
		String orgFileName = null;
		String imgFileName = null;
		String isNewFile = "N"; // 업데이트 할 파일 체크 판단 변수
		
		// 이미지 파일이 존재하는지 확인
		while (multipartIter.hasNext())
		{
			multipartFile = multipartReq.getFile(multipartIter.next());
		}
		
		try 
		{
			// 수정할 상품 데이터가 존재하는지 확인 - 부모 테이블 
			if (fooditemRepo.existsById(fid))
			{
				// 기존 이미지 파일을 사용하는 경우
				if (multipartFile == null || multipartFile.isEmpty())
				{
					orgFileName = "";
					imgFileName = "";
				}
				else
				{
					// 새로운 이미지 파일로 변경하는 경우
					HashMap<String, Object> fileNameMap = fileUtils.getFileName(multipartFile, fooditemDto.getFoodname());
					if (fileNameMap.isEmpty() == false) 
					{
						orgFileName = fileNameMap.get("orgFileName").toString(); // 새로운 원본 파일 이름
						imgFileName = fileNameMap.get("imgFileName").toString(); // 새로운 파일 이름
						isNewFile = "Y";
					}
				}
				
				// 수정할 상품을 조회하여 불러옴 - 부모 테이블
				Fooditem fooditemEntity = fooditemRepo.findById(fid).get();
				
				// 부모 테이블 객체
				FooditemResponseDto fooditemResDto = new FooditemResponseDto();
				BeanUtils.copyProperties(fooditemEntity, fooditemResDto);
				
				// 수정할 상품 데이터를 설정(setter)
				fooditemResDto.setFoodname(fooditemDto.getFoodname());
				fooditemResDto.setFoodprice(fooditemDto.getFoodprice());
				fooditemResDto.setStock(fooditemDto.getStock());
				
				// 수정할 상품 이미지 정보를 조회하여 불러옴 - 자식 테이블 (자식의 id값을 set하기위해 조회를 함)
				Foodimgfile foodImgFileEntity = foodimgfileRepo.findByFooditem_fid(fid);
				String beforeImgFileName = foodImgFileEntity.getImgfilename(); // 기존 파일 이름
				
				// 파일을 교체하는 것이 아니라면 기존의 파일 정보를 저장함
				orgFileName = orgFileName.equals("") ? foodImgFileEntity.getOrgfilename() : orgFileName;
				imgFileName = imgFileName.equals("") ? foodImgFileEntity.getImgfilename() : imgFileName;
				
				// 자식 테이블 객체
				FoodimgfileResponseDto foodImgFileResDto = new FoodimgfileResponseDto();
				BeanUtils.copyProperties(foodImgFileEntity, foodImgFileResDto);
				
				// 수정할 상품 이미지 데이터를 설정(setter)
				foodImgFileResDto.setImgfilename(imgFileName);
				foodImgFileResDto.setOrgfilename(orgFileName);
				foodImgFileResDto.setFooditem(fooditemEntity);
				
				// 부모 테이블 객체 정보에 자식 테이블 정보 객체를 설정(setter)
				fooditemResDto.setFoodimgfile(foodImgFileResDto.toEntity());
				
				// 상품 정보 및 상품 이미지 정보 수정 <- cascade(ALL)로 인해 부모 테이블을 수정 시 자식 테이블도 함께 변경됨
				Fooditem saveCheckEntity = fooditemRepo.save(fooditemResDto.toEntity());
				
				// 파일 업데이트
				if (saveCheckEntity != null)
				{
					String fileProcExceptionMsg = fileUtils.parseUpdateFile(isNewFile, multipartFile, beforeImgFileName, imgFileName);
					returnMsg = fileProcExceptionMsg.equals("") ? returnMsg : "상품 정보가 수정되었습니다.";
				}
			}
		} 
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
			// fid가 NULL인 경우
			returnMsg = "수정할 항목이 존재하지 않습니다.";
		}
		
		return returnMsg;
	}
	
	/**
	 * 매니저 상품 삭제 처리 서비스
	 * args -------------------------------
	 * fidMap					| 삭제할 상품 고유 아이디 정보
	 * ------------------------------------
	 * return data ------------------------
	 * delProcException			| 삭제 처리 결과 정보 [0: 삭제 처리 성공, 1: 삭제 처리 중 문제 발생]
	 * ------------------------------------
	 */
	@Transactional
	public int deleteFooditem(HashMap<String, Long[]> findMap) {
		int delProcException = 0;
		for (Long fid : findMap.get("fid"))
		{
			try 
			{
				Foodimgfile foodImgFileEntity = foodimgfileRepo.findByFooditem_fid(fid);
				
				foodimgfileRepo.deleteByFooditem_fid(fid); // 음식 이미지 정보 삭제 - 자식 테이블
				
				fooditemRepo.deleteById(fid); // 음식 정보 삭제 - 부모 테이블
				
				delProcException = fileUtils.deleteFile(foodImgFileEntity.getImgfilename());
			} 
			catch (IllegalArgumentException e) // 쿼리 처리 중 예외 발생 
			{
				e.printStackTrace();
				delProcException = 1;
			}
		}
		
		return delProcException;
	}
	
	
	
	
	/**
	 * 상품 상태 수정 처리 서비스
	 * args -------------------------------
	 * map						| 상태 수정할 상품 고유 아이디, 변경할 상태 정보 {status : 0 or 1, fid : [1, 2, 3, 4, ...]}
	 * ------------------------------------
	 * return data ------------------------
	 * procException			| 상태 수정 처리 결과 정보 [0: 수정 처리 성공, 1: 수정 처리 중 문제 발생]
	 * ------------------------------------
	 */
	@Transactional
	public int changeFooditemStatus(HashMap<String, Object> map) {
		int procException = 0;
		
		int changeStatus = Integer.valueOf(map.get("status").toString());
		String[] fidListStr = map.get("fid").toString().split(","); // [1, 2, 3, 4...]로 저장되는 fid의 값을 Long[]타입으로 가공
		Long[] fidArr = new Long[fidListStr.length];
		
		// String 형의 fid를 Long형으로 변환
		for (int i = 0; i < fidListStr.length; i += 1)
		{
			// [1] 이 문자열에서 substring으로 '[', ']' 문자를 제거
			fidArr[i] = Long.valueOf(fidListStr[i].substring(1, 2)).longValue();
		}
		
		try 
		{
			List<Fooditem> changedDtoList = new ArrayList<Fooditem>();
			
			for (int i = 0; i < fidArr.length; i += 1)
			{
				// 상태를 변경할 대상 항목을 검색
				Fooditem fooditemEntity = fooditemRepo.findById(fidArr[i]).get();
				FooditemResponseDto responseDto = new FooditemResponseDto();
				BeanUtils.copyProperties(fooditemEntity, responseDto);
				// 상품의 판매 상태값 변경
				responseDto.setStatus(changeStatus);
				
				changedDtoList.add(responseDto.toEntity());
			}
			
			// 일괄 Update
			fooditemRepo.saveAll(changedDtoList);
		} 
		catch (IllegalArgumentException e) {
			// findStatus가 NULL이거나 changedDtoList가 NULL인 경우
			procException = 1;
		}
		
		return  procException;
	}
}
