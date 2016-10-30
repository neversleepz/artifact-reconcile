Dreamstime Filename Reconciler
--------------------------------

Hacky little utility to rename files downloaded from dreamstime in the form `dreamstime_size_id.xtn` to include a 
sanitized file name.

# Instructions
1. Download your files from [My Downloads](https://www.dreamstime.com/downloads.php) into a directory of your choice.  You'll have to click the **Download Again** link to do so for each one.
2. Download the Excel Archive.  Its currently a tab called **Download Excel Archive**
3. Extract Excel Archive, open the .xls file in Excel. 
4. Save the file in .xlsx format
5. Run the app ```reconciler -l /path/to/excel-archive.xlsx -i /path/to/downloaded/images -m```

## Zero Confidence & No use to anyone
This is an alpha cut put on Github to satify my ego and wear down my self-esteem at the same time.
