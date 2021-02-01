function TO_MYSQL_DATE(org){

    return org.toISOString().slice(0, 19).replace('T', ' ');

}