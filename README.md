# GWT Phonebook Service #

This is [Google Web Toolkit (GWT)](http://www.gwtproject.org/) based web application uses for phonebook creation. You can add, edit, remove, delete, search and print list of all records. 

## Used frameworks ##

* [gwt-presenter](https://code.google.com/p/gwt-presenter/)
* [gwt-dispatch](https://code.google.com/p/gwt-dispatch/)

## Deployment ##

We've used [WildFly](http://wildfly.org/) (version 8.2.0 Final)application server to deploy this application. To be able to run this application you must create java:/datasources/GWTPhoneBook datasource (we've used datasource based on [MySQL driver](http://www.mysql.com/products/connector/)). Database for application named "gwt_phonebook".

## License ##

This application distributed under GPLv2 license, so you can use it freely for any purposes.