package com.artexceptionals.youreuro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.artexceptionals.youreuro.database.CashRecordDatabase;
import com.artexceptionals.youreuro.helpers.CurrencyHelper;
import com.artexceptionals.youreuro.model.Account;
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
import java.util.ArrayList;
import java.util.List;

public class ExportManager {
    private static ExportManager instance;
    private Context mContext;
    private static final String SUMMARY_FILE = "YourEuroSummary.pdf";
    public static final int  STORAGE_PERMISSION_REQUEST_CODE = 111;
    private final CashRecordDatabase cashRecordDatabase;
    CustomSharedPreferences sharedPreferences;
    List<CashRecord> cashRecords;
    private File pdfFile;

    public ExportManager(Context context,CustomSharedPreferences sharedPreferences, CashRecordDatabase cashRecordDatabase) {
        this.sharedPreferences = sharedPreferences;
        this.cashRecordDatabase = cashRecordDatabase;
        mContext = context;
    }

    public static ExportManager getInstance(Context context) {
        if (instance == null) {
            instance = new ExportManager(context,CustomSharedPreferences.getInstance(YourEuroApp.getAppContext()),
                    CashRecordDatabase.getCashRecordDatabase(context));
        }
        return instance;
    }

    public void createPdf(){
        cashRecords = cashRecordDatabase.cashRecordDao().getAll();

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("YourEuro", "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(), SUMMARY_FILE);
        OutputStream output = null;
        try {
            output = new FileOutputStream(pdfFile);

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
                cells[j].setBackgroundColor(BaseColor.LIGHT_GRAY);
            }

            if(cashRecords.size()>0) {
                for (int i = 0; i < cashRecords.size(); i++) {

                    CashRecord cashRecord = cashRecords.get(i);
                    String timeStamp = DateFormat.getDateInstance(DateFormat.SHORT).format(cashRecords.get(i).getTimeStamp());

                    table.addCell(String.valueOf(cashRecord.getCategory().getCatagoryName()));
                    table.addCell(String.valueOf(cashRecord.getPaymentType()));
                    table.addCell(String.valueOf(cashRecord.getCashRecordType()));
                    table.addCell(String.valueOf(timeStamp));
                    table.addCell(String.format("%.2f",cashRecord.getAmount()) + " " +CurrencyHelper.getSymbolName(cashRecords.get(i).getCurrency()));
                }
            }

            PdfPTable balanceTable = new PdfPTable(new float[]{3, 3, 3});
            balanceTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            balanceTable.getDefaultCell().setFixedHeight(50);
            balanceTable.setTotalWidth(PageSize.A4.getWidth());
            balanceTable.setWidthPercentage(100);
            balanceTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            balanceTable.addCell("Currency");
            balanceTable.addCell("Symbol");
            balanceTable.addCell("Amount");
            table.setHeaderRows(1);

            PdfPCell[] balanceCells = balanceTable.getRow(0).getCells();
            for (int j = 0; j < balanceCells.length; j++) {
                balanceCells[j].setBackgroundColor(BaseColor.LIGHT_GRAY);
            }

            List<Account> accountList = new ArrayList<>();
            for (CashRecord cashRecord: cashRecords){
                Account account = new Account(cashRecord.getCurrency(), cashRecord.getAmount());

                if (accountList.contains(account)){
                    accountList.get(accountList.indexOf(account)).setBalance(accountList.get(accountList.indexOf(account)).getBalance() + account.getBalance());
                }else {
                    accountList.add(account);
                }
            }

            for (Account account: accountList){
                balanceTable.addCell(String.valueOf(CurrencyHelper.getName(account.getCurrency())));
                balanceTable.addCell(CurrencyHelper.getSymbolName(account.getCurrency()));
                balanceTable.addCell(String.valueOf(account.getBalance()));
            }

            PdfWriter.getInstance(document, output);
            document.open();
            Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.BLUE);
            Font g = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.BLUE);

            document.add(new Paragraph("Summary of transactions", f));
            document.add(new Paragraph("\n",g));

            document.add(new Paragraph("Details of all the records:", g));
            document.add(new Paragraph("\n",g));
            document.add(table);
            document.add(new Paragraph("\n",g));

            document.add(new Paragraph("Net balance:" , g));
            document.add(new Paragraph("\n",g));
            document.add(balanceTable);
            document.close();

            Uri myUri = FileProvider.getUriForFile(YourEuroApp.getAppContext(), YourEuroApp.getAppContext().getPackageName() + ".MyFileProvider",pdfFile);
            ((MainActivity)mContext).grantUriPermission("com.artexceptionals.youreuro", myUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        sendEmail();
    }

    public void sendEmail() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Documents", SUMMARY_FILE);
        Uri myUri = FileProvider.getUriForFile(YourEuroApp.getAppContext(), YourEuroApp.getAppContext().getPackageName() + ".MyFileProvider",file);
        mContext.grantUriPermission("com.artexceptionals.youreuro", myUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        String to = sharedPreferences.genericGetString("user_Email","Not Set");
        if (file.exists()) {
            if(!("Not Set".equalsIgnoreCase(to))) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("application/pdf");

//                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                // the attachment
                emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
                // the mail subject
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "summary PDF");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Summary from YourEuro Money Control App");
                mContext.startActivity(emailIntent);
            }else {
                Toast.makeText(mContext, "Set user Email in settings", Toast.LENGTH_LONG).show();
            }
        }
    }
}
