package com.worldspon.toy.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component("fileUtils")
public class FileUtils {
	// 파일 저장 위치
	//private static final String filePath = "D:\\file storage\\project\\toy\\img\\fooditem\\";
	private static final String filePath = "D:\\git_directory\\Toy\\Toy\\src\\main\\resources\\static\\img\\fooditem\\";
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	/**
	 * 이미지 파일 이름 구하는 메소드
	 * args -------------------------------
	 * multipartFile			| 이미지 파일 객체 정보
	 * foodname					| 이미지 파일의 변경 이름 정보
	 * return data ------------------------
	 * map						| 이미지 파일의 원본, 변경 이름 정보
	 * ------------------------------------
	 */
	public HashMap<String, Object> getFileName(MultipartFile multipartFile, String foodname) {
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		String randomUUID = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if (multipartFile != null && multipartFile.isEmpty() == false)
		{
			originalFileName = multipartFile.getOriginalFilename();	// 이미지 파일 이름 + 확장자 추출
			originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));	// 이미지 파일 확장자 정보 구하기
			randomUUID = CommonUtils.getRandomString();
			storedFileName = randomUUID + "-" + foodname + originalFileExtension;	// 저장할 파일 이름 지정 EX) 메뉴 이름 + 확장자
			
			map.put("orgFileName", originalFileName);
			map.put("imgFileName", storedFileName);
		}
		
		return map;
	}
	
	
	
	
	/**
	 * 이미지 파일 처리 메소드
	 * args -------------------------------
	 * multipartReq				| multipart 요청 정보
	 * fileName					| 이미지 파일 이름 정보
	 * return data ------------------------
	 * map						| 이미지 파일 처리 결과 및 오류코드 정보
	 * ------------------------------------
	 */
	public HashMap<String, Object> parseInsertFile(MultipartFile multipartFile, String fileName) {
		/* 단일 파일 삭제 처리 */
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		// 파일 저장 위치에 폴더 생성
		File file = new File(filePath);

		if (file.exists() == false)
		{
			file.mkdir();
		}
			
		
		if (multipartFile.isEmpty() == false)
		{
			try
			{
				// 저장될 경로에 파일 정보 생성
				file = new File(filePath + fileName);
				
				// 파일을 생성
				multipartFile.transferTo(file);
				map.put("fileException", 1);
				
			}
			catch (NullPointerException e)
			{
				e.printStackTrace();
				// filePath와 fileName이 NULL인 경우
				map.put("fileException", -2);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
				// 파일을 읽거나 쓰기 중 에러가 발생하는 경우
				map.put("fileException", 0);
				map.put("msg", "파일 처리 중 문제가 발생하였습니다.");
			}
			catch (IllegalStateException e2)
			{
				e2.printStackTrace();
				// 파일이 이미 이동되어 다른 파일을 전송 처리할 수 없는 경우
				map.put("fileException", -1);
				map.put("msg", "더 이상 파일을 처리할 수 없습니다.");
			}
		}
		
		return map;
		
		/*
		 * 여러 파일 삭제 처리
		 * 
		// 이미지 파일 이름 객체
		Iterator<String> fileIter = multipartReq.getFileNames();
		int index = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		// 파일 저장 위치에 폴더 생성
		File file = new File(filePath);

		if (file.exists() == false)
		{
			file.mkdir();
		}
		
		
		// 이미지 파일 생성 시작
		while (fileIter.hasNext())
		{
			
			MultipartFile multipartFile = multipartReq.getFile(fileIter.next());
			
			try {
				
				if (multipartFile.isEmpty() == false)
				{
					file = new File(filePath + listFileNames.get(index));
					try 
					{
						
						multipartFile.transferTo(file);
						map.put("fileException", 1);
					} 
					catch (IOException e) 
					{
						
						map.put("fileException", 0);
						map.put("msg", "파일 처리 중 문제가 발생하였습니다.");
					} 
					catch (IllegalStateException e2) 
					{

						map.put("fileException", -1);
						map.put("msg", "더 이상 파일을 처리할 수 없습니다.");
					}
				}
				
			} 
			catch (NullPointerException e) 
			{
				
				map.put("fileException", -2);
				map.put("msg", "처리할 파일이 존재하지 않습니다.");
			}
			
			index += 1;
		}
		*/
	}
	
	
	/**
	 * 이미지 파일 수정 메소드
	 * args -------------------------------
	 * isNewFile				| 파일 업데이트 판단 여부 값 [N: 기존 파일 리네임 처리, Y: 신규 파일 생성]
	 * multipartReq				| multipart 요청 정보
	 * beforeFileName			| 이전에 업로드 된 파일이름
	 * afterFileName			| 신규로 만들 파일 이름 or 기존 파일 리네임할 이름
	 * return data ------------------------
	 * map						| 이미지 파일 처리 결과 및 오류코드 정보
	 * ------------------------------------
	 */
	public String parseUpdateFile(String isNewFile, MultipartFile multipartFile, String beforeFileName, String afterFileName) {
		String fileProcExceptionMsg = "";
		
		try 
		{
			// 기존 파일 이름의 객체를 생성
			File beforeFile = new File(filePath + beforeFileName);
			File afterFile = new File(filePath + afterFileName);
			
			if (beforeFile.exists())
			{
				if (isNewFile.equals("N"))
				{
					// 기존 파일 리네임
					beforeFile.renameTo(afterFile);
					fileProcExceptionMsg = "상품 정보가 수정되었습니다.";
				}
				else
				{
					// 기존 파일을 삭제함
					if (beforeFile.delete())
					{
						// 업데이트 할 파일을 생성함
						multipartFile.transferTo(afterFile);
						fileProcExceptionMsg = "상품 정보가 수정되었습니다.";
					}
				}
			}
		} 
		catch (NullPointerException e) 
		{
			e.printStackTrace();
			// 기존 파일을 찾지 못했을 경우
			fileProcExceptionMsg = "원본 파일이 존재하지 않거나 손상되었습니다.";
		}
		catch (SecurityException e1)
		{
			e1.printStackTrace();
			// 보안 관리 프로그램으로 인해 메소드가 파일에 액세스를 거부당했을 경우
			fileProcExceptionMsg = "문서 보안이나 기타 보안 프로그램으로 인하여 파일에 접근이 실패했습니다.";
		}
		catch (IOException e2)
		{
			e2.printStackTrace();
			// 파일을 읽거나 쓰기 중 에러가 발생하는 경우
			fileProcExceptionMsg = "파일을 생성하는 도중 문제가 발생했습니다. 다시 시도해주세요.";
		}
		
		return fileProcExceptionMsg;
	}
	
	
	/**
	 * 이미지 파일 삭제 메소드
	 * args -------------------------------
	 * fileName					| 이미지 파일 이름 정보
	 * return data ------------------------
	 * fileProcException		| 이미지 파일 처리 결과 및 오류코드 정보
	 * ------------------------------------
	 */
	public int deleteFile(String fileName) {
		int fileProcException = 0;
		
		try 
		{
			File file = new File(filePath + fileName);
			
			if (file.exists())
			{
				file.delete();
			}
		} 
		catch (NullPointerException e) 
		{
			e.printStackTrace();
			// filePath와 fileName이 NULL인 경우
			fileProcException = -1;
		}
		catch (SecurityException e1)
		{
			e1.printStackTrace();
			// 보안 관리 프로그램으로 인해 메소드가 파일에 액세스를 거부당했을 경우
			fileProcException = -2;
		}
		
		return fileProcException;
	}
}
