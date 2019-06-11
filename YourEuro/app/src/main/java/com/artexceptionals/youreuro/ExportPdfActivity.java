package com.artexceptionals.youreuro;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.artexceptionals.youreuro.database.CashRecordDatabase;
import com.artexceptionals.youreuro.model.CashRecord;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.List;


public class ExportPdfActivity extends AppCompatActivity {
    private CashRecordDatabase cashRecordDatabase;
    private static final String TAG = "PdfCreatorActivity";
    final private int RequestPermission = 111;
    private File pdfFile;
    List<CashRecord> cashRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_pdf);
        cashRecordDatabase = CashRecordDatabase.getCashRecordDatabase(ExportPdfActivity.this);
    }

    public void ExportPdf(View view) {
        cashRecords = cashRecordDatabase.cashRecordDao().getAll();
        try {
            createPdfWrapper();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void createPdfWrapper() throws FileNotFoundException, DocumentException{
        int hasWritePermission = ActivityCompat.checkSelfPermission(ExportPdfActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(hasWritePermission != PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {

                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                RequestPermission);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        RequestPermission);
            }
            return;
        } else {
            createPdf();
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ExportPdfActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        String pdfname = "monthlySummary.pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{3, 3, 3, 3, 3});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell("Category Name");
        table.addCell("Payment Method");
        table.addCell("Type");
        table.addCell("Date");
        table.addCell("Amount");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }

        if(cashRecords.size()>0) {
            for (int i = 0; i < cashRecords.size(); i++) {

                String cashRecordType = cashRecords.get(i).getCashRecordType();
                String paymentType = cashRecords.get(i).getPaymentType();
                String categoryName = cashRecords.get(i).getCategory().getCatagoryName();
                String timeStamp = DateFormat.getDateInstance(DateFormat.SHORT).format(cashRecords.get(i).getTimeStamp());
                float amount = cashRecords.get(i).getAmount();

                table.addCell(String.valueOf(categoryName));
                table.addCell(String.valueOf(paymentType));
                table.addCell(String.valueOf(cashRecordType));
                table.addCell(String.valueOf(timeStamp));
                table.addCell(String.valueOf(amount));

            }
        }

        PdfWriter.getInstance(document, output);
        document.open();
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.BLUE);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.BLUE);
        document.add(new Paragraph("Monthly Summary \n\n", f));
        document.add(new Paragraph("YourEuro", g));
        document.add(table);
        document.close();
//        Log.e("Madhu", cashRecords.toString());
        previewPdf();
    }

    private void previewPdf() {

        PackageManager packageManager = ExportPdfActivity.this.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            ExportPdfActivity.this.startActivity(intent);
        } else {
            Toast.makeText(ExportPdfActivity.this, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }

}