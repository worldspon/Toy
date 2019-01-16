package com.worldspon.toy.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("fileUtils")
public class FileUtils {
	// 파일 저장 위치
	//private static final String filePath = "D:\\file storage\\project\\toy\\img\\fooditem\\";
	private static final String filePath = "D:\\git_directory\\Toy\\Toy\\src\\main\\resources\\static\\img\\fooditem\\";
	
	
	/**
	 * 이미지 파일 이름 구하는 메소드
	 * args -------------------------------
	 * multipartFile			| 이미지 파일 객체 정보
	 * foodname					| 이미지 파일의 변경 이름 정보
	 * return data ------------------------
	 * map						| 이미지 파일의 원본, 변경 이름 정보
	 * ------------------------------------
	 */
	public HashMap<String, Object> getFileName(MultipartFile multipartFile, String foodname) throws Exception {
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		String randomUUID = null;
		
		HashMap<String, Object> map = null;
		
		if (multipartFile.isEmpty() == false)
		{
			originalFileName = multipartFile.getOriginalFilename();	// 이미지 파일 이름 + 확장자 추출
			originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));	// 이미지 파일 확장자 정보 구하기
			randomUUID = CommonUtils.getRandomString();
			storedFileName = randomUUID + "-" + foodname + originalFileExtension;	// 저장할 파일 이름 지정 EX) 메뉴 이름 + 확장자
			
			map = new HashMap<String, Object>();
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
			
		
		try {
			
			if (multipartFile.isEmpty() == false)
			{
				// 저장될 경로에 파일 정보 생성
				file = new File(filePath + fileName);
				
				try 
				{
					// 파일을 생성
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
	
	
	
	public int deleteFile(String fileName) {
		int fileProcException = 0;
		try 
		{
			File file = new File(filePath + fileName);
			if (file.exists())
			{
				try 
				{
					file.delete();
				} 
				catch (SecurityException e) 
				{
					e.printStackTrace();
					fileProcException = -2;
					// 보안 관리 프로그램으로 인해 메소드가 파일에 액세스를 거부당했을 경우
				}
			}
		} 
		catch (NullPointerException e) 
		{
			e.printStackTrace();
			// 파일을 업로드 할 폴더 경로가 존재하지 않는 경우
			fileProcException = -1;
		}
		
		return fileProcException;
	}
}
