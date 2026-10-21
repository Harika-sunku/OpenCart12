package Utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

    // DataProvider 1

    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException
    {
        String path = ".\\testdata\\OpenCart_LoginData.xlsx"; // taking xl file from testData

        ExcelUtilities xlutil = new ExcelUtilities(path); // creating an object for XLUtility

        int totalrows = xlutil.getRowCount("Login");
        int totalcols = xlutil.getCellCount("Login", 1);

        String logindata[][] = new String[totalrows][totalcols];

        // read the data from xl storing in two dimensional array
        for (int i = 1; i <= totalrows; i++)
        {
            for (int j = 0; j < totalcols; j++)
            {
                logindata[i - 1][j] = xlutil.getCellData("Login", i, j);
            }
        }

        return logindata; // returning two dimension array
    }
}