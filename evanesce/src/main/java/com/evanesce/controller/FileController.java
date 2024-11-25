package com.evanesce.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.evanesce.message.ResponseFile;
import com.evanesce.message.ResponseMessage;
import com.evanesce.entity.FileDB;
import com.evanesce.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@CrossOrigin // ("http://localhost:3000")
public class FileController {

	@Autowired
	private FileStorageService storageService;

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam String requestid, @RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			logger.info("Entering uploadFile method.");
			int reqimgid = Integer.parseInt(requestid);
			logger.info("Request ID: {}", reqimgid);

			storageService.store(file, reqimgid);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			logger.info(message);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			logger.error(message, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@GetMapping("/files")
	public ResponseEntity<List<ResponseFile>> getListFiles() {
		logger.info("Fetching list of files.");
		List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
			String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/files/")
					.path(dbFile.getId().toString())
					.toUriString();
			logger.info("File download URL: {}", fileDownloadUrl);

			return new ResponseFile(dbFile.getName(), fileDownloadUrl, dbFile.getType(), dbFile.getData().length);
		}).collect(Collectors.toList());

		logger.info("Returning {} files.", files.size());
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping("/files/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
		logger.info("Fetching file with ID: {}", id);
		FileDB fileDB = storageService.getFile(id);

		logger.info("Returning file: {}", fileDB.getName());
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
				.body(fileDB.getData());
	}
}
