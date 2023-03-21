package com.ZJHealth.ZJHealth.service;

import com.ZJHealth.ZJHealth.dto.PrescriptionDto;
import com.ZJHealth.ZJHealth.model.Prescription;
import com.ZJHealth.ZJHealth.model.ZJUser;
import com.ZJHealth.ZJHealth.repository.PrescriptionRepository;
import com.ZJHealth.ZJHealth.repository.ZJUserRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Data
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final ZJUserRepository zjUserRepository;
    @Autowired
    private HttpServletRequest request;

    public void createPrescription(PrescriptionDto prescriptionReq) {
        Principal currentUser = request.getUserPrincipal();
        ZJUser doctor = zjUserRepository.findByEmailIgnoreCase(currentUser.getName());
        ZJUser patient = zjUserRepository.findByEmailIgnoreCase(prescriptionReq.getPatientEmail());
        Prescription prescription = new Prescription();
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setName(prescriptionReq.getName());
        prescription.setPharmacy(prescriptionReq.getPharmacy());
        prescription.setQuantity(prescriptionReq.getQuantity());
        prescription.setCondition(prescriptionReq.getCondition());
        prescriptionRepository.save(prescription);

    }

    public List<Prescription> listAll() {
        return prescriptionRepository.findAll();
    }

    public List<Prescription> listByPatient() {
        Principal currentUser = request.getUserPrincipal();
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(currentUser.getName());
        return prescriptionRepository.findAllByPatientId(user.getId());
    }

    public Document createPdf(HttpServletResponse response, Long id) throws IOException {
        Prescription prescription = prescriptionRepository.findById(id).get();
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER);
        Paragraph paragraph = new Paragraph("Prescription For "+prescription.getCondition(), font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        document.add(new Paragraph("\n"));

        font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        paragraph = new Paragraph("Patient and Doctor Information", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(5);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(2);
        table.addCell("Prescribed By: Dr."+prescription.getDoctor().getLastName());
        table.addCell("Doctor ID: "+prescription.getDoctor().getId());
        table.addCell("Patient First Name "+prescription.getPatient().getFirstName());
        table.addCell("Patient Last Name: "+prescription.getPatient().getLastName());
        table.addCell("Patient State: "+prescription.getPatient().getState());
        table.addCell("Patient Address: "+prescription.getPatient().getStreet()+", "+prescription.getPatient().getCity());
        table.addCell("Patient Zipcode: "+prescription.getPatient().getZip());
        table.addCell("Patient Phone Number: "+prescription.getPatient().getPhoneNum());
        document.add(table);

        paragraph = new Paragraph("Medication Information", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(5);
        document.add(paragraph);

        table = new PdfPTable(2);
        table.addCell("Medication: "+prescription.getName());
        table.addCell("Quantity: "+prescription.getQuantity());
        table.addCell("Pharmacy: "+prescription.getPharmacy());
        table.addCell("Condition: "+prescription.getCondition());
        document.add(table);

        LocalDateTime accessed = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy-HH:mm");
        paragraph = new Paragraph("Accessed At: "+accessed.format(dateFormat), font);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.setSpacingBefore(5);
        document.add(paragraph);

        document.close();
        return document;
    }
}
