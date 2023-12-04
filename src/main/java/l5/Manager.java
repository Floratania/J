package l5;


import static l5.TaxDB.createTaxTable;
import static l5.TaxDB.dropTaxTable;
import static l5.TaxInspectorDB.createTaxInspectorTable;
import static l5.TaxInspectorDB.dropTaxInspectorTable;
import static l5.TaxPayerDB.createTaxPayerTable;
import static l5.TaxPayerDB.dropTaxPayerTable;

public class Manager{

    void createDatabaseStructure() {
        createTaxTable();
        createTaxInspectorTable();
        createTaxPayerTable();
    }


    void dropDatabaseStructure(){
        dropTaxTable();
        dropTaxPayerTable();
        dropTaxInspectorTable();
    }

}
