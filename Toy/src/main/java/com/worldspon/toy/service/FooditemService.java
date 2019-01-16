package com.worldspon.toy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.worldspon.toy.dto.foodimgfile.FoodimgfileRequestDto;
import com.worldspon.toy.dto.fooditem.FooditemRequestArrayDto;
import com.worldspon.toy.dto.fooditem.FooditemRequestDto;
import com.worldspon.toy.dto.fooditem.FooditemResponseDto;
import com.worldspon.toy.entity.Foodimgfile;
import com.worldspon.toy.entity.Fooditem;
import com.worldspon.toy.repository.FoodimgfileRepository;
import com.worldspon.toy.repository.FooditemRepository;
import com.worldspon.toy.utils.CommonUtils;
import com.worldspon.toy.utils.FileUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FooditemService {

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
		List<Fooditem> entityList = fooditemRepo.findByStatus(1);
		
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
				
				try {
					
					HashMap<String, Object> fileMap = fileUtils.getFileName(multipartFile, dto.getFoodname());
					listFileNames.add(fileMap.get("imgFileName").toString());
					
					// 음식 메뉴 파일 정보 복사
					FoodimgfileRequestDto imgfiledto = new FoodimgfileRequestDto();
					imgfiledto.setImgfilename(fileMap.get("imgFileName").toString());
					imgfiledto.setOrgfilename(fileMap.get("orgFileName").toString());
					imgfiledto.setFooditem(dto.toEntity());
					
					try 
					{
						// 자식 테이블 insert 수행
						foodimgfileRepo.save(imgfiledto.toEntity());
						
						// 이미지 파일을 생성함
						HashMap<String, Object> map = fileUtils.parseInsertFile(multipartFile, imgfiledto.getImgfilename());
						// fileException | -2: NullPointer, -1: IllegalState, 0: IO, 1: Success
						int exception = Integer.valueOf(map.get("fileException").toString()); 
						
						// 에러가 발생한 경우
						if (exception <= 0)
						{
							msg = map.get("msg").toString();
						}
						else
						{
							msg = "상품이 등록되었습니다.";
						}
						
					} 
					catch (Exception e) 
					{
						msg = "상품 등록 중 문제가 발생하였습니다.";
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
	 * dto				| 음식 메뉴 정보 객체
	 * return data ------------------------
	 * msg				| 음식 메뉴 등록 처리 결과 정보
	 * ------------------------------------
	 */
	public String modifyFooditem(FooditemRequestDto dto) throws Exception {
		
		return "";
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
}
