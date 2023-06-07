package com.Controller;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.Config.pdfService;

@Controller
@RequestMapping("/download")
public class PDFController {
	@Autowired
	private pdfService pdfservice;
	@Autowired
	private XLService xlservice;

	@PostMapping("/createpdf")
	public ResponseEntity<InputStreamResource> createpdf() {

		ByteArrayInputStream in = pdfservice.createpdf();
		HttpHeaders head = new HttpHeaders();
		head.add("Content-Disposition", "inline:file=Contacts.pdf");

		return ResponseEntity.ok().headers(head).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(in));
	}
	@RequestMapping("/createxl")
	public ResponseEntity<Resource> download()
	{
		String fileName="Contatcs.xlsx";
		ByteArrayInputStream data = xlservice.getData();
		InputStreamResource file=new InputStreamResource(data);
		
		
		 ResponseEntity<Resource> body = ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachments;filename"+fileName )
		.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
		 
		 return body;
	}
	
	
	

}
