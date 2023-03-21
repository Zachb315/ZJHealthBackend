package com.ZJHealth.ZJHealth.service;

import com.ZJHealth.ZJHealth.dto.LabCreateDto;
import com.ZJHealth.ZJHealth.dto.LabResultDto;
import com.ZJHealth.ZJHealth.dto.LabReturnDto;
import com.ZJHealth.ZJHealth.model.*;
import com.ZJHealth.ZJHealth.repository.DoctorRepository;
import com.ZJHealth.ZJHealth.repository.LabRepository;
import com.ZJHealth.ZJHealth.repository.MedicalRecordRepository;
import com.ZJHealth.ZJHealth.repository.ZJUserRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LabService {
    private final LabRepository labRepository;
    private final DoctorRepository doctorRepository;
    private final ZJUserRepository zjUserRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private HttpServletRequest request;

    public void createLab(LabCreateDto newLab) {
        Principal currentUser = request.getUserPrincipal();
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(currentUser.getName());
        ZJUser patient = zjUserRepository.findByEmailIgnoreCase(newLab.getPatientEmail());
        Lab lab = new Lab();
        Doctor doctor = user.getDoctor();
        lab.setDate(newLab.getDate());
        lab.setTestName(newLab.getTestName());
        lab.setDoctor(doctor);
        lab.setPatient(patient);
        lab.setRecord(patient.getRecord());
        labRepository.save(lab);
        doctor.addLab(lab);
        doctorRepository.save(doctor);
        patient.getRecord().addLab(lab);
        zjUserRepository.save(patient);
    }

    public List<Lab> listall() {
        return labRepository.findAll();
    }

    public List<LabReturnDto> listByDoctor() {
        Principal currentUser = request.getUserPrincipal();
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(currentUser.getName());
        List<Lab> labs = labRepository.findAllByDoctorId(user.getDoctor().getId());
        List<LabReturnDto> ret = new ArrayList<>();
        for (int i=0; i<labs.size(); i++) {
            LabReturnDto dto = new LabReturnDto();
            Lab curr = labs.get(i);
            dto.setDoctor(curr.getDoctor());
            dto.setId(curr.getId());
            dto.setRecord(curr.getRecord());
            dto.setTestName(curr.getTestName());
            dto.setResult(curr.getResult());
            dto.setPatientFName(curr.getPatient().getFirstName());
            dto.setPatientLName(curr.getPatient().getLastName());
            dto.setDate(curr.getDate());
            ret.add(dto);
        }
        return ret;
    }

    public void updateResult(LabResultDto labdto) {
        Lab lab = labRepository.findById(labdto.getId()).get();
        lab.setResult(labdto.getResult());
        labRepository.save(lab);

    }

    public Document viewPdf(HttpServletResponse response, Long id) throws IOException {
        Lab lab = labRepository.findById(id).get();
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER);
        Paragraph paragraph = new Paragraph("Lab Test Form", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        document.add(new Paragraph("\n"));

        font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        paragraph = new Paragraph("Lab and Doctor Information", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(5);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(2);
        table.addCell("Lab Scheduled By: Dr."+lab.getDoctor().getUser().getLastName());
        table.addCell("Date Scheduled: "+lab.getDate());
        table.addCell("Test Name: "+lab.getTestName());
        table.addCell("Test Result: "+lab.getResult());
        table.addCell("Patient First Name: "+lab.getPatient().getFirstName());
        table.addCell("Patient Last Name: "+lab.getPatient().getLastName());

        document.add(table);

        document.close();
        return document;


    }
}
