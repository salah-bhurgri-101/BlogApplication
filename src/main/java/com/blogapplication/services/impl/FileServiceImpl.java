package com.blogapplication.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogapplication.services.FileService;
@Service
public class FileServiceImpl implements FileService {

	@Override
	public String updoadImage(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		//file naem
		String name = file.getOriginalFilename();
		
		//random one generete file
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
		
		//fullPath
		String filePath = path + File.separator +fileName1;
		
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		//file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String fullPath = path +File.separator+fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;
	}

}
