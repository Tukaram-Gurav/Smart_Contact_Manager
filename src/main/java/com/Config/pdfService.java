package com.Config;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entities.Contact;
import com.Repositary.ContactRepositary;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

@Service
public class pdfService {
	@Autowired
	private ContactRepositary cpo;
	private Logger logger=LoggerFactory.getLogger(pdfService.class);
	
	
	public ByteArrayInputStream createpdf()
	{
		logger.info("PDF Creation is on the way");
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		
		
		 Document document = new Document(PageSize.A4);  
		 
		    // Getting instance of PdfWriter
		 PdfWriter.getInstance(document, out);
		    // Opening the created document to change it
		    document.open();
		    // Creating font
		    // Setting font style and size
		    Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		    fontTiltle.setSize(20);
		    // Creating paragraph
		    Paragraph paragraph1 = new Paragraph("List of the Contacts", fontTiltle);
		    // Aligning the paragraph in the document
		    paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
		    // Adding the created paragraph in the document
		    document.add(paragraph1);
		    // Creating a table of the 3 columns
		    PdfPTable  table = new PdfPTable(3); 
		   
		    // Setting width of the table, its columns and spacing
		    table.setWidthPercentage(100f);
		    table.setWidths(new int[] {3,5,5});
		    table.setSpacingBefore(5);
		    // Create Table Cells for the table header
		    PdfPCell cell = new PdfPCell();
		    
		    // Setting the background color and padding of the table cell
		    cell.setBackgroundColor(CMYKColor.BLUE);
		    cell.setPadding(8);
		    // Creating font
		    // Setting font style and size
		    Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		    font.setColor(CMYKColor.WHITE);
		    // Adding headings in the created table cell or  header
		    // Adding Cell to table
		   
		    cell.setPhrase(new Phrase("Contact Name", font));
		    table.addCell(cell);
		    cell.setPhrase(new Phrase("Email", font));
		    table.addCell(cell);
		    cell.setPhrase(new Phrase("Mobile No", font));
		    table.addCell(cell);  
		    
		    List<Contact> ContactList = cpo.findAll();
			// Iterating the list of Contacts
		    for (Contact student: ContactList) {
		    
		     
		      // Adding student name
		      table.addCell(student.getName());
		      
		      // Adding student email
		      table.addCell(student.getEmail());
		      // Adding student mobile
		      table.addCell(student.getPhone());
		    }
		    

		    // Adding the created table to the document
		    document.add(table);
		    // Closing the document
		    document.close();
		    
		    return new ByteArrayInputStream(out.toByteArray());
		    
		    
		  }

}
