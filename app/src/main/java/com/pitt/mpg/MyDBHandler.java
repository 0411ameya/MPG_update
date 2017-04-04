package com.pitt.mpg;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteCursor;
import android.content.Context;
import android.content.ContentValues;


public class MyDBHandler extends SQLiteOpenHelper{

    public static  final int DATABASE_VERSION = 1;
    public static  final String DATABASE_NAME = "requestdetails.db";
    public static  final String TABLE_REQUEST = "request";
    public static  final String COLUMN_ID = "_id";
    public static  final String COLUMN_CITY = "city";
    public static  final String COLUMN_LAT = "lat";
    public static  final String COLUMN_LNG = "lng";
    public static  final String COLUMN_RADIUS = "radius";
    public static  final String COLUMN_VENUES = "venues";
    public static  final String COLUMN_ip_artANDentertainment = "art_and_entertainment";
    public static  final String COLUMN_ip_collegeANDuniversity = "college_and_university";
    public static  final String COLUMN_ip_event = "event";
    public static  final String COLUMN_ip_food = "food";
    public static  final String COLUMN_ip_nightlifespot = "night_life_spot";
    public static  final String COLUMN_ip_outdoorsANDrecreation = "outdoors_and_recreation";
    public static  final String COLUMN_ip_professionalANDothers = "professional_and_others";
    public static  final String COLUMN_ip_residence = "residence";
    public static  final String COLUMN_ip_shopANDservice = "shop_and_service";
    public static  final String COLUMN_ip_travelANDtransport = "travel_and_transport";
    //profiles tab
    public static  final String COLUMN_pr_artLOVER = "art_lover";
    public static  final String COLUMN_pr_nightLOVER = "night_lover";
    public static  final String COLUMN_pr_outdoorsLOVER = "outdoors_lover";
    public static  final String COLUMN_pr_foodLOVER = "food_lover";
    public static  final String COLUMN_pr_shoppingLOVER = "shopping_lover";
    //algorithms tab
    public static  final String COLUMN_al_PDcomposite = "pd_composite";
    public static  final String COLUMN_al_PDdistpref = "pd_dist_pref";
    public static  final String COLUMN_al_pagerank = "page_rank";
    public static  final String COLUMN_al_PDpopdist = "pd_pop_dist";
    public static  final String COLUMN_al_PDpref = "pd_pref";
    public static  final String COLUMN_al_PDcompositepagerank = "pd_composite_page_rank";
    public static  final String COLUMN_al_PDprefdistpagerank = "pd_pref_dist_page_rank";
    public static  final String COLUMN_al_KMedoids = "k_medoids";
    public static  final String COLUMN_al_DisC = "disc";
    public static  final String COLUMN_al_RandomSelection = "random_selection";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + TABLE_REQUEST + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT "
                //INPUT
                + COLUMN_CITY + " TEXT "
                + COLUMN_LAT + " TEXT "
                + COLUMN_LNG + " TEXT "
                + COLUMN_RADIUS + " TEXT "
                + COLUMN_VENUES + " TEXT "
                + COLUMN_ip_artANDentertainment + " TEXT "
                + COLUMN_ip_collegeANDuniversity + " TEXT "
                + COLUMN_ip_event + " TEXT "
                + COLUMN_ip_food + " TEXT "
                + COLUMN_ip_nightlifespot + " TEXT "
                + COLUMN_ip_outdoorsANDrecreation + " TEXT "
                + COLUMN_ip_professionalANDothers + " TEXT "
                + COLUMN_ip_residence + " TEXT "
                + COLUMN_ip_shopANDservice + " TEXT "
                + COLUMN_ip_travelANDtransport + " TEXT "
                //PROFILES
                + COLUMN_pr_artLOVER + " TEXT "
                + COLUMN_pr_foodLOVER + " TEXT "
                + COLUMN_pr_nightLOVER + " TEXT "
                + COLUMN_pr_outdoorsLOVER + " TEXT "
                + COLUMN_pr_shoppingLOVER + " TEXT "
                //ALGORITHMS
                + COLUMN_al_PDcomposite + " TEXT "
                + COLUMN_al_PDdistpref + " TEXT "
                + COLUMN_al_pagerank + " TEXT "
                + COLUMN_al_PDpopdist + " TEXT "
                + COLUMN_al_PDpref + " TEXT "
                + COLUMN_al_PDcompositepagerank + " TEXT "
                + COLUMN_al_PDprefdistpagerank + " TEXT "
                + COLUMN_al_KMedoids + " TEXT "
                + COLUMN_al_DisC + " TEXT "
                + COLUMN_al_RandomSelection + " TEXT "
                + ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
        onCreate(sqLiteDatabase);
    }

    public void addQuery(RequestDetails rd) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY, rd.get_city());
        values.put(COLUMN_LAT, rd.get_lat());
        values.put(COLUMN_LNG, rd.get_lat());
        values.put(COLUMN_RADIUS, rd.get_radius());
        values.put(COLUMN_VENUES, rd.get_venues());
        //INPUT
        values.put(COLUMN_ip_artANDentertainment, rd.is_ip_artANDentertainment());
        values.put(COLUMN_ip_collegeANDuniversity, rd.is_ip_collegeANDuniversity());
        values.put(COLUMN_ip_event, rd.is_ip_event());
        values.put(COLUMN_ip_food, rd.is_ip_food());
        values.put(COLUMN_ip_nightlifespot, rd.is_ip_nightlifespot());
        values.put(COLUMN_ip_outdoorsANDrecreation, rd.is_ip_outdoorsANDrecreation());
        values.put(COLUMN_ip_professionalANDothers, rd.is_ip_professionalANDothers());
        values.put(COLUMN_ip_residence, rd.is_ip_residence());
        values.put(COLUMN_ip_shopANDservice, rd.is_ip_shopANDservice());
        values.put(COLUMN_ip_travelANDtransport, rd.is_ip_travelANDtransport());
        //PROFILES
        values.put(COLUMN_pr_artLOVER, rd.is_pr_artLOVER());
        values.put(COLUMN_pr_foodLOVER, rd.is_pr_foodLOVER());
        values.put(COLUMN_pr_nightLOVER, rd.is_pr_nightLOVER());
        values.put(COLUMN_pr_outdoorsLOVER, rd.is_pr_outdoorsLOVER());
        values.put(COLUMN_pr_shoppingLOVER, rd.is_pr_shoppingLOVER());
        //ALGORITHMS
        values.put(COLUMN_al_PDcomposite, rd.is_al_PDcomposite());
        values.put(COLUMN_al_PDdistpref, rd.is_al_PDdistpref());
        values.put(COLUMN_al_pagerank, rd.is_al_pagerank());
        values.put(COLUMN_al_PDpopdist, rd.is_al_PDcomposite());
        values.put(COLUMN_al_PDpref, rd.is_al_PDcomposite());
        values.put(COLUMN_al_PDcompositepagerank, rd.is_al_PDcomposite());
        values.put(COLUMN_al_PDprefdistpagerank, rd.is_al_PDcomposite());
        values.put(COLUMN_al_KMedoids, rd.is_al_PDcomposite());
        values.put(COLUMN_al_DisC, rd.is_al_PDcomposite());
        values.put(COLUMN_al_RandomSelection, rd.is_al_PDcomposite());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_REQUEST, null, values);
        db.close();
    }

}
