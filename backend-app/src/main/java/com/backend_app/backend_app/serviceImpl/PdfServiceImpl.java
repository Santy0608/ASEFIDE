package com.backend_app.backend_app.serviceImpl;

import com.backend_app.backend_app.dto.AporteDTO;
import com.backend_app.backend_app.dto.ReporteDTO;
import com.backend_app.backend_app.repository.ReporteStoredProcedureRepository;
import com.backend_app.backend_app.service.PdfService;
import com.backend_app.backend_app.views.UsuarioViewRepository;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.util.List;


@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private UsuarioViewRepository usuarioViewRepository;

    @Autowired
    private ReporteStoredProcedureRepository reporteStoredProcedureRepository;

    @Override
    public byte[] generarReportePdf(List<ReporteDTO> reportes) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            pdf.setDefaultPageSize(PageSize.A4.rotate());

            Document document = new Document(pdf);
            document.setMargins(20, 20, 20, 20);

            // Obtener número de folio
            Long numeroFolio = reporteStoredProcedureRepository.obtenerNumeroReporte();

            Table headerTable = new Table(UnitValue.createPercentArray(new float[]{1, 1})).useAllAvailableWidth();
            headerTable.addCell(new Cell().add(new Paragraph("SISTEMA ASEFIDE")
                            .setBold().setFontSize(20).setFontColor(ColorConstants.DARK_GRAY))
                    .setBorder(Border.NO_BORDER));

            headerTable.addCell(new Cell().add(new Paragraph(
                            "Reporte Histórico de Transacciones\n" +
                                    "Folio: " + numeroFolio + "\n" +
                                    "Generado el: " + java.time.LocalDateTime.now()
                                    .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                            .setTextAlignment(TextAlignment.RIGHT).setFontSize(10).setItalic())
                    .setBorder(Border.NO_BORDER));

            document.add(headerTable);
            document.add(new Paragraph("\n"));

            float[] columnWidths = {3, 10, 8, 8, 10, 6, 8, 10, 7, 12};
            Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

            String[] headers = {"ID", "Tipo", "Inicio", "Final", "Módulo", "Reg.", "Monto", "Generado", "Estado", "Usuario"};
            for (String h : headers) {
                table.addHeaderCell(new Cell().add(new Paragraph(h).setBold().setFontColor(ColorConstants.WHITE))
                        .setBackgroundColor(ColorConstants.DARK_GRAY)
                        .setTextAlignment(TextAlignment.CENTER));
            }

            int rowIdx = 0;
            for (ReporteDTO r : reportes) {
                boolean isEven = rowIdx % 2 == 0;

                table.addCell(createStyledCell(String.valueOf(r.getIdReporte()), isEven, TextAlignment.CENTER));
                table.addCell(createStyledCell(r.getNombreTipoReporte(), isEven, TextAlignment.LEFT));
                table.addCell(createStyledCell(r.getFechaInicio().toString(), isEven, TextAlignment.CENTER));
                table.addCell(createStyledCell(r.getFechaFinal().toString(), isEven, TextAlignment.CENTER));
                table.addCell(createStyledCell(r.getNombreModuloReporte(), isEven, TextAlignment.LEFT));
                table.addCell(createStyledCell(String.valueOf(r.getTotalRegistros()), isEven, TextAlignment.CENTER));

                String monto = "₡" + String.format("%.2f", r.getResumenMontos());
                table.addCell(createStyledCell(monto, isEven, TextAlignment.RIGHT).setBold());

                table.addCell(createStyledCell(r.getFechaGeneracion().toString(), isEven, TextAlignment.CENTER));
                table.addCell(createStyledCell(r.getNombreEstado(), isEven, TextAlignment.CENTER));
                table.addCell(createStyledCell(r.getNombreUsuario() + " " + r.getApellidoPaterno(), isEven, TextAlignment.LEFT));

                rowIdx++;
            }

            document.add(table);
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF profesional", e);
        }
    }

    @Override
    public byte[] generarReporteAportePdf(List<AporteDTO> aportes) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            pdf.setDefaultPageSize(PageSize.A4);

            Document document = new Document(pdf);
            document.setMargins(30, 30, 30, 30);


            Table headerTable = new Table(UnitValue.createPercentArray(new float[]{60, 40})).useAllAvailableWidth();

            headerTable.addCell(new Cell().add(new Paragraph("SISTEMA ASEFIDE")
                            .setBold().setFontSize(18).setFontColor(ColorConstants.BLUE))
                    .setBorder(Border.NO_BORDER));

            String fechaGen = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            headerTable.addCell(new Cell().add(new Paragraph("Estado de Aportes\nGenerado: " + fechaGen)
                            .setTextAlignment(TextAlignment.RIGHT).setFontSize(10).setItalic())
                    .setBorder(Border.NO_BORDER));

            document.add(headerTable);

            if (!aportes.isEmpty()) {
                document.add(new Paragraph("Asociado: " + aportes.get(0).getNombreCompleto())
                        .setBold().setFontSize(12).setMarginTop(10));
                document.add(new Paragraph("Usuario: " + aportes.get(0).getNombreUsuario())
                        .setFontSize(10).setFontColor(ColorConstants.GRAY));
            }

            document.add(new Paragraph("\n"));

            float[] columnWidths = {2, 6, 4, 4, 4};
            Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

            String[] headers = {"ID", "Nombre Completo", "Monto Aporte", "Afiliación", "Estado"};
            for (String h : headers) {
                table.addHeaderCell(new Cell().add(new Paragraph(h).setBold().setFontColor(ColorConstants.WHITE))
                        .setBackgroundColor(ColorConstants.DARK_GRAY)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setPadding(5));
            }

            int rowIdx = 0;
            for (AporteDTO a : aportes) {
                boolean isEven = rowIdx % 2 == 0;
                Color rowColor = isEven ? new DeviceRgb(245, 245, 245) : ColorConstants.WHITE;

                table.addCell(createStyledCell(String.valueOf(a.getIdUsuario()), rowColor, TextAlignment.CENTER));
                table.addCell(createStyledCell(a.getNombreCompleto(), rowColor, TextAlignment.LEFT));

                String montoFormatted = "¢" + String.format("%,.2f", a.getAporteMensual());
                table.addCell(createStyledCell(montoFormatted, rowColor, TextAlignment.RIGHT).setBold());

                table.addCell(createStyledCell(a.getFechaAfiliacion().toString(), rowColor, TextAlignment.CENTER));
                table.addCell(createStyledCell(a.getEstadoUsuario(), rowColor, TextAlignment.CENTER));

                rowIdx++;
            }

            document.add(table);
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF de aportes", e);
        }
    }


    private Cell createStyledCell(String text, boolean isEven, TextAlignment align) {
        Cell cell = new Cell().add(new Paragraph(text).setFontSize(9));
        if (!isEven) {
            cell.setBackgroundColor(ColorConstants.LIGHT_GRAY, 0.2f); // Efecto zebra
        }
        cell.setTextAlignment(align);
        return cell;
    }

    private Cell createStyledCell(String text, Color backgroundColor, TextAlignment alignment) {
        return new Cell().add(new Paragraph(text != null ? text : ""))
                .setBackgroundColor(backgroundColor) // Ahora aceptará ambos tipos
                .setTextAlignment(alignment)
                .setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 0.5f))
                .setPadding(5)
                .setFontSize(10);
    }
}
