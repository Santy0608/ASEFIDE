package com.backend_app.backend_app.service;

import com.backend_app.backend_app.dto.AporteDTO;
import com.backend_app.backend_app.dto.ReporteDTO;

import java.util.List;

public interface PdfService {

    byte[] generarReportePdf(List<ReporteDTO> reportes);

    byte[] generarReporteAportePdf(List<AporteDTO> aportes);

}
