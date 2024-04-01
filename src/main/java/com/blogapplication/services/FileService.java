package com.blogapplication.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String updoadImage(String path , MultipartFile file) throws IOException;
	
//	InputStream getResource(String path , String fileName)
	InputStream getResource(String path, String fileName) throws FileNotFoundException;
}
