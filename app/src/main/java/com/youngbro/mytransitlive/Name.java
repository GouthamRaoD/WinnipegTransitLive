package com.youngbro.mytransitlive;

/**
 * Created by Goutham on 2017-03-05.
 */

public class Name {
    public static String getSName(String str)
    {
        String result ="";
        switch(str){
            case "1-0-F" :
                result = "The Forks";
                break;
            case "1-0-D" :
                result = "Downtown";
                break;
            case "2-0-F" :
                result = "The Forks";
                break;
            case "2-0-D" :
                result = "Downtown";
                break;
            case "3-1-C" :
                result = "City Hall";
                break;
            case "3-1-U" :
                result = "University of Winnipeg";
                break;
            case "10-0-SP" :
                result = "St. Boniface";
                break;
            case "10-1-WT" :
                result = "Wolseley via Tache";
                break;
            case "10-0-ST" :
                result = "St. Boniface via Tache";
                break;
            case "10-1-WP" :
                result = "Wolseley via Provencher";
                break;
            case "10-1-W" :
                result = "Wolseley";
                break;
            case "10-1-P#" :
                result = "Downtown via Provencher";
                break;
            case "10-1-T#" :
                result = "Downtown via Tache";
                break;
            case "10-0-#" :
                result = "Downtown";
                break;
            case "11-0-P" :
                result = "Polo Park";
                break;
            case "11-1-G" :
                result = "Glenway";
                break;
            case "11-0-W" :
                result = "Westwood";
                break;
            case "11-1-D" :
                result = "Kildonan via Donwood";
                break;
            case "11-0-C" :
                result = "Crestview";
                break;
            case "11-1-#" :
                result = "Downtown";
                break;
            case "11-0-#" :
                result = "University of Winnipeg";
                break;
            case "11-0-S" :
                result = "St. Charles";
                break;
            case "11-1-R":
                result ="North Kildonan";
                break;
            case "11-0-*":
                result ="Henderson";
                break;
            case "11-1-##":
                result ="Portage & Rouge";
                break;
            case "11-0-**":
                result ="Portage & Woodhaven";
                break;
            case "11-1-M":
                result ="City Hall";
                break;
            case "12-1-C":
                result ="City Hall";
                break;
            case "12-0-P":
                result ="Polo Park";
                break;
            case "12-0-H":
                result ="HSC";
                break;
            case "12-0-V":
                result ="Valour & Portage";
                break;
            case "14-1-E":
                result ="Ferry Road";
                break;
            case "14-0-D":
                result ="South St. Vital via Dakota";
                break;
            case "14-0-P":
                result ="South St. Vital via Paddington";
                break;
            case "14-1-##":
                result ="St. Mary's & Dakota";
                break;
            case "14-1-#":
                result ="Downtown";
                break;
            case "14-0-#":
                result ="Downtown";
                break;
            case "15-0-W":
                result ="Airport via Wellington";
                break;
            case "15-1-C":
                result ="Mountain via Church";
                break;
            case "15-1-L":
                result ="Mountain & Fife";
                break;
            case "15-0-F":
                result ="Airport via Flight Road";
                break;
            case "15-1-I":
                result ="Mountain via Inkster";
                break;
            case "15-0-#":
                result ="Downtown";
                break;
            case "15-0-L":
                result ="Mountain and Fife";
                break;
            case "15-1-#":
                result ="Downtown";
                break;
            case "16-1-L":
                result ="Island Lakes";
                break;
            case "16-0-*":
                result ="Selkirk & McPhillips";
                break;
            case "16-1-P":
                result ="Plaza Dr via St. Vital Centre";
                break;
            case "16-0-B":
                result ="Tyndall Park via Burrows";
                break;
            case "16-0-M":
                result ="Tyndall Park via Manitoba";
                break;
            case "16-1-##":
                result ="Southdale Centre";
                break;
            case "16-1-V":
                result ="St.Vital Centre";
                break;
            case "16-0-#":
                result ="Downtown";
                break;
            case "16-1-K":
                result ="Kingston Row";
                break;
            case "16-1-*":
                result ="Selkirk & McPhillips";
                break;
            case "16-0-K":
                result ="Kingston Row";
                break;
            case "16-1-O":
                result ="Osborne Junction";
                break;
            case "16-1-#":
                result ="Downtown";
                break;
            case "17-0-M":
                result ="Maples";
                break;
            case "17-1-MH":
                result ="Misericordia Health Centre";
                break;
            case "17-1-G":
                result ="Garden City Centre";
                break;
            case "17-0-A":
                result ="Amber Trails";
                break;
            case "17-0-S":
                result ="Seven Oaks Hospital";
                break;
            case "17-0-#":
                result ="Downtown";
                break;
            case "17-1-B":
                result ="Memorial & Broadway";
                break;
            case "17-1-#":
                result ="Downtown";
                break;
            case "18-0-J":
                result ="Garden City Centre";
                break;
            case "18-1-E":
                result ="Tuxedo";
                break;
            case "18-0-R":
                result ="Riverbend";
                break;
            case "18-1-M":
                result ="Main & McAdam";
                break;
            case "18-1-C":
                result ="Corydon & Cambridge";
                break;
            case "18-0-#":
                result ="Downtown";
                break;
            case "18-0-T":
                result ="Main & Templeton";
                break;
            case "18-1-#":
                result ="Downtown";
                break;
            case "18-1-A":
                result ="Assiniboine Park";
                break;
            case "19-0-N":
                result ="Red River College";
                break;
            case "19-1-A":
                result ="Windsor Park";
                break;
            case "19-0-L":
                result ="Red River College";
                break;
            case "19-1-#":
                result ="Downtown";
                break;
            case "19-1-D":
                result ="Windsor Park";
                break;
            case "19-1-*":
                result ="Downtown";
                break;
            case "19-0-E":
                result ="Elizabeth & Drake";
                break;
            case "19-0-#":
                result ="Downtown";
                break;
            case "20-1-A":
                result ="Airport Terminal";
                break;
            case "20-0-W":
                result ="Watt & Leighton";
                break;
            case "20-0-F":
                result ="Fort & Portage";
                break;
            case "20-1-R":
                result ="Redwood & Main";
                break;
            case "20-1-T":
                result ="Portage & Tylehurst";
                break;
            case "20-1-M":
                result ="Memorial & Broadway";
                break;
            case "20-1-#":
                result ="Downtown";
                break;
            case "21-0-W":
                result =" Westwood";
                break;
            case "21-1-D":
                result ="City Hall";
                break;
            case "21-0-C":
                result ="Crestview";
                break;
            case "21-0-G":
                result ="Grace Hospital";
                break;
            case "21-0-S":
                result ="St.Charles";
                break;
            case "21-1-*":
                result ="Portage & Woodhaven";
                break;
            case "21-1-#":
                result ="Portage & Rouge";
                break;
            case "22-0-W":
                result ="Westwood";
                break;
            case "22-1-D":
                result ="City Hall";
                break;
            case "22-0-C":
                result ="Crestview";
                break;
            case "22-1-*":
                result ="Portage & Woodhaven";
                break;
            case "22-0-S":
                result ="St.Charles";
                break;
            case "22-1-#":
                result ="Portage & Rouge";
                break;
            case "24-0-U":
                result ="Unicity";
                break;
            case "24-1-D":
                result ="City Hall";
                break;
            case "24-1-P":
                result ="Polo Park";
                break;
            case "24-1-#":
                result ="Portage & Tylehurst";
                break;
            case "25-1-D":
                result ="City Hall";
                break;
            case "25-0-U":
                result ="Unicity";
                break;
            case "26-0-P":
                result ="Polo Park";
                break;
            case "26-1-C":
                result ="City Hall";
                break;
            case "26-0-T":
                result ="Portage & Tylehurst";
                break;
            case "28-1-D":
                result ="City Hall";
                break;
            case "28-0-I":
                result ="Inkster Industrial Park";
                break;
            case "28-0-O":
                result ="Omands Creek";
                break;
            case "28-0-R":
                result ="Red River College";
                break;
            case "29-0-C":
                result ="City Hall";
                break;
            case "29-1-L":
                result ="Logan";
                break;
            case "29-0-W":
                result ="Windermere";
                break;
            case "30-0-I":
                result ="Inkster Park";
                break;
            case "30-1-D":
                result ="Downtown";
                break;
            case "31-0-O":
                result =" Oak Point";
                break;
            case "31-1-D":
                result ="City Hall";
                break;
            case "31-0-M":
                result ="Meadows West";
                break;
            case "32-0-R":
                result ="Riverbend";
                break;
            case "32-1-D":
                result ="Downtown";
                break;
            case "32-1-M":
                result ="Main & Seven Oaks";
                break;
            case "32-0-L":
                result ="N.Main Express";
                break;
            case "33-0-M":
                result ="Maples via Mapleglen";
                break;
            case "33-1-F":
                result ="Fort & Portage";
                break;
            case "33-0-J":
                result ="Maples";
                break;
            case "33-1-C":
                result ="City Hall";
                break;
            case "33-1-P":
                result ="Main & Pioneer";
                break;
            case "33-1-#":
                result =" McPhillips & Mapleglen";
                break;
            case "34-1-D":
                result ="Downtown";
                break;
            case "34-0-M":
                result ="Maples";
                break;
            case "35-0-A":
                result ="Amber Trails";
                break;
            case "35-1-C":
                result ="City Hall";
                break;
            case "36-1-C":
                result ="HSC";
                break;
            case "36-0-U":
                result ="University Of Manitoba";
                break;
            case "36-1-M":
                result ="HSC";
                break;
            case "38-0-T":
                result ="Templeton & McPhillips";
                break;
            case "38-1-M":
                result ="William Stephenson & Main";
                break;
            case "38-1-F":
                result ="The Forks";
                break;
            case "38-1-D":
                result ="Portage \u0026 Fort";
                break;
            case "38-1-S":
                result ="Salter";
                break;
            case "39-0-T":
                result ="Taylor Park & Ride";
                break;
            case "39-1-D":
                result ="City Hall";
                break;
            case "40-0-S":
                result ="All Seasons";
                break;
            case "40-1-D":
                result ="Balmoral Station";
                break;
            case "41-1-D":
                result ="Balmoral Station";
                break;
            case "41-0-G":
                result ="Glenway";
                break;
            case "41-0-N":
                result ="North Kildonan";
                break;
            case "42-1-D":
                result ="Balmoral Station";
                break;
            case "42-0-L":
                result ="Lakeside Meadows";
                break;
            case "43-0-K":
                result ="Kildonan Place";
                break;
            case "43-1-D":
                result ="Assiniboine";
                break;
            case "44-0-M":
                result ="Kildonan Place";
                break;
            case "44-1-L":
                result ="Broadway via London";
                break;
            case "44-0-L":
                result ="Kildonan Place";
                break;
            case "44-1-M":
                result ="Broadway via Louelda";
                break;
            case "44-1-D":
                result ="Downtown";
                break;
            case "44-1-*":
                result ="London & Munroe";
                break;
            case "45-0-K":
                result ="Kildonan Place";
                break;
            case "45-1-D":
                result ="Downtown";
                break;
            case "45-1-#":
                result ="Main & Pioneer";
                break;
            case "45-0-M":
                result ="Munroe & Prevette";
                break;
            case "46-0-R":
                result ="Transcona via Regent";
                break;
            case "46-1-P":
                result ="Transcona Express";
                break;
            case "46-0-K":
                result ="Transcona via Kildare";
                break;
            case "46-1-D":
                result ="Balmoral Station";
                break;
            case "47-0-R":
                result ="Transcona via Regent";
                break;
            case "47-1-D":
                result ="Balmoral Station";
                break;
            case "47-0-K":
                result ="Transcona via Kildare";
                break;
            case "47-1-#":
                result ="Kildonan Place";
                break;
            case "47-1-P":
                result ="Main & Portage";
                break;
            case "48-1-D":
                result ="Balmoral Station";
                break;
            case "48-0-T":
                result ="North Transcona";
                break;
            case "49-0-S":
                result ="Dugald & Beghin";
                break;
            case "49-1-D":
                result ="Balmoral Station";
                break;
            case "49-0-T":
                result ="North Transcona";
                break;
            case "50-0-S":
                result ="Sage Creek";
                break;
            case "50-1-D":
                result ="University of Winnipeg";
                break;
            case "50-1-W":
                result ="Windsor Park";
                break;
            case "51-0-U":
                result ="University Of Manitoba";
                break;
            case "51-1-S":
                result ="St. Vital Centre";
                break;
            case "53-1-D":
                result ="University of Winnipeg";
                break;
            case "53-0-N":
                result ="Norwood";
                break;
            case "54-1-D":
                result ="University of Winnipeg";
                break;
            case "54-0-A":
                result ="St. Amant";
                break;
            case "54-0-S":
                result ="South St. Vital";
                break;
            case "55-0-M":
                result ="St.Vital Centre";
                break;
            case "55-1-D":
                result ="University of Winnipeg";
                break;
            case "55-0-D":
                result ="St. Vital Centre";
                break;
            case "55-1-M":
                result =" University of Winnipeg";
                break;
            case "55-1-##":
                result ="St. Anne's & Niakwa";
                break;
            case "55-1-#":
                result ="Portage & Garry";
                break;
            case "56-0-S":
                result ="St. Boniface";
                break;
            case "56-1-D":
                result ="University of Winnipeg";
                break;
            case "56-1-#":
                result ="Portage & Garry";
                break;
            case "57-0-S":
                result ="Southdale";
                break;
            case "57-1-D":
                result ="University of Winnipeg";
                break;
            case "58-1-D":
                result ="City Hall";
                break;
            case "58-0-A":
                result ="South St. Vital";
                break;
            case "58-0-W":
                result ="South St. Vital";
                break;
            case "59-1-D":
                result ="University of Winnipeg";
                break;
            case "59-0-A":
                result ="Aldgate";
                break;
            case "59-0-I":
                result ="Island Lakes";
                break;
            case "60-0-U":
                result ="University of Manitoba";
                break;
            case "60-1-D":
                result ="Downtown";
                break;
            case "64-0-L":
                result ="Lindenwoods";
                break;
            case "64-1-D":
                result ="Downtown";
                break;
            case "65-0-W":
                result ="Westdale";
                break;
            case "65-1-R":
                result ="Roblin & Dieppe";
                break;
            case "65-1-D":
                result ="Downtown";
                break;
            case "65-1-K":
                result ="Kenaston";
                break;
            case "66-1-D":
                result ="Downtown";
                break;
            case "66-0-P":
                result ="Polo Park";
                break;
            case "66-0-U":
                result ="Unicity";
                break;
            case "66-1-K":
                result ="Grant & Kenaston";
                break;
            case "66-0-C":
                result ="Dieppe Loop";
                break;
            case "67-0-K#":
                result ="Westdale";
                break;
            case "67-1-K":
                result ="City Hall";
                break;
            case "68-0-G":
                result ="Grosvenor & Renfrew";
                break;
            case "68-1-W":
                result ="University of Winnipeg";
                break;
            case "68-1-#":
                result ="Stradbrook & Osborne";
                break;
            case "71-0-M#":
                result ="WalMart via McPhillips";
                break;
            case "71-1-S":
                result ="Portage via Sinclair";
                break;
            case "71-0-S#":
                result ="Templeton via Sinclair";
                break;
            case "71-1-M":
                result ="Portage via McPhillips";
                break;
            case "71-1-#":
                result ="Arlington & Mountain";
                break;
            case "72-1-U":
                result ="University of Manitoba";
                break;
            case "72-0-K":
                result ="Richmond West via Killarney";
                break;
            case "72-0-D":
                result ="Richmond West via Dalhousie";
                break;
            case "75-0-U":
                result ="University of Manitoba";
                break;
            case "75-1-K":
                result ="Kildonan Place";
                break;
            case "75-1-S":
                result ="Speers- Elizabeth";
                break;
            case "76-1-S":
                result ="St.Vital Centre";
                break;
            case "76-0-U":
                result ="University of Manitoba";
                break;
            case "77-0-P":
                result ="Polo Park";
                break;
            case "77-1-K":
                result ="Kildonan Place";
                break;
            case "77-1-G":
                result ="Garden City Centre";
                break;
            case "77-1-R":
                result ="Red River College";
                break;
            case "77-0-G":
                result ="Garden City Centre";
                break;
            case "77-1-M":
                result ="Main & Margaret";
                break;
            case "77-0-M":
                result ="Main & Margaret";
                break;
            case "77-0-#":
                result ="Keewatin & Inkster";
                break;
            case "77-1-H":
                result ="Henderson & Whellams";
                break;
            case "78-1-K":
                result ="Polo Park via Kenaston";
                break;
            case "78-0-K":
                result ="UofM via Kenaston";
                break;
            case "78-1-C":
                result ="Polo Park via Cambridge";
                break;
            case "78-0-C":
                result ="UofM via Cambridge";
                break;
            case "79-1-P":
                result ="Portage via Kenaston";
                break;
            case "79-1-K":
                result ="Polo Park via Kenaston";
                break;
            case "79-0-K#":
                result ="Westdale via Kenaston";
                break;
            case "82-0-U":
                result ="Unicity";
                break;
            case "82-1-G":
                result ="Grace Hospital";
                break;
            case "83-1-M":
                result ="Murray Park";
                break;
            case "83-0-U":
                result ="Unicity";
                break;
            case "83-1-T":
                result ="Thompson & Ness";
                break;
            case "83-1-G":
                result ="Grace Hospital";
                break;
            case "83-0-A":
                result ="Crestview & Ashern";
                break;
            case "84-0-W":
                result ="Whyte Ridge";
                break;
            case "84-1-S":
                result ="Stafford Loop";
                break;
            case "85-1-W":
                result ="Henderson & Whellams";
                break;
            case "85-0-K":
                result ="Kildonan Place";
                break;
            case "85-0-A":
                result ="All Seasons";
                break;
            case "86-0-W":
                result ="Whyte Ridge";
                break;
            case "86-1-S":
                result ="";
                break;
            case "Stafford Loop":
                result ="";
                break;
            case "87-0-B":
                result ="Beghin & Dugald";
                break;
            case "87-1-C":
                result ="Crossroads Station";
                break;
            case "87-0-S":
                result ="South Transcona";
                break;
            case "88-0-F":
                result ="Fife";
                break;
            case "88-1-C":
                result ="Cathedral & Main";
                break;
            case "89-1-K":
                result ="Kildonan Place";
                break;
            case "89-0-T":
                result ="North Transcona";
                break;
            case "89-1-D":
                result ="Day \u0026 Regent";
                break;
            case "90-0-T":
                result ="orth Transcona";
                break;
            case "90-1-W":
                result ="Henderson & Whellams";
                break;
            case "90-0-K":
                result ="Kildonan Place";
                break;
            case "91-0-S":
                result ="St.Norbert";
                break;
            case "91-1-K":
                result ="Killarney";
                break;
            case "92-0-L":
                result ="Lakeside Meadows";
                break;
            case "92-1-C":
                result ="Crossroads Station";
                break;
            case "92-1-K":
                result ="Kildonan Place";
                break;
            case "93-1-S":
                result ="St. Vital Centre";
                break;
            case "93-0-H":
                result ="Highbury";
                break;
            case "94-1-P":
                result ="Windermere";
                break;
            case "94-1-WP":
                result ="Windermere";
                break;
            case "94-0-K":
                result ="Kenaston Common";
                break;
            case "94-1-W":
                result ="Wildwood Park";
                break;
            case "95-0-pp":
                result ="Polo Park";
                break;
            case "95-1-R":
                result ="Riverview";
                break;
            case "95-0-S":
                result ="Shaftesbury Park";
                break;
            case "95-0-F":
                result ="Fort Rouge Station";
                break;
            case "95-1-m":
                result ="Morley";
                break;
            case "95-0-p":
                result ="Pan Am Pool";
                break;
            case "95-1-F":
                result ="Fort Rouge Station";
                break;
            case "96-1-S":
                result ="St. Vital Centre";
                break;
            case "96-0-P":
                result ="Paterson Loop";
                break;
            case "96-0-L":
                result ="Southdale Centre";
                break;
            case "96-1-L":
                result ="Southdale Centre";
                break;
            case "97-1-P":
                result ="Point Douglas";
                break;
            case "97-0-F":
                result ="Fife";
                break;
            case "98-1-G":
                result ="Grace Hospital";
                break;
            case "98-0-U":
                result ="Unicity";
                break;
            case "98-0-W":
                result ="Westdale";
                break;
            case "99-0-M":
                result ="Misericordia Health Centre";
                break;
            case "99-1-D":
                result ="Downtown";
                break;
            case "99-0-D":
                result ="Downtown";
                break;
            case "99-0-W":
                result ="Windermere";
                break;
            case "99-1-S":
                result ="Stradbrook";
                break;
            case "101-0":
                result ="101";
                break;
            case "101-1":
                result ="DART 101";
                break;
            case "102-0":
                result ="DART 102";
                break;
            case "102-1":
                result ="DART 102";
                break;
            case "109-0":
                result ="DART 109";
                break;
            case "109-1-?":
                result ="DART 109";
                break;
            case "110-0":
                result ="DART 110";
                break;
            case "110-1":
                result ="DART 110";
                break;
            case "137-1-D":
                result ="Richmond Super Express";
                break;
            case "137-0-U":
                result ="Richmond Super Express";
                break;
            case "137-0-S":
                result ="St. Norbert";
                break;
            case "160-1-D":
                result ="Downtown";
                break;
            case "160-0-U":
                result ="University Of Manitoba";
                break;
            case "161-1-D":
                result ="Downtown";
                break;
            case "161-0-U":
                result ="University Of Manitoba";
                break;
            case "162-1-D":
                result ="Downtown";
                break;
            case "162-0-S":
                result ="St. Norbert";
                break;
            case "162-0-U":
                result ="University Of Manitoba";
                break;
            case "162-1-K":
                result ="Killarney";
                break;
            case "162-0-T":
                result ="Turnbull Drive";
                break;
            case "163-0-W":
                result ="Bridgwater Forest";
                break;
            case "163-1-P":
                result ="Pembina & Univ. Cresc.";
                break;
            case "163-1-D":
                result ="Downtown";
                break;
            case "170-1-D":
                result ="Downtown";
                break;
            case "170-1-K":
                result ="Killarney";
                break;
            case "170-0-U":
                result ="University Of Manitoba";
                break;
            case "170-0-S":
                result ="St, Norbert";
                break;
            case "180-0-K":
                result ="Scurfield";
                break;
            case "180-1-D":
                result ="Downtown";
                break;
            case "181-0-W":
                result ="Whyte Ridge";
                break;
            case "181-1-C":
                result ="Pembina";
                break;
            case "181-1-D":
                result ="Downtown";
                break;
            case "183-1-D":
                result ="Downtown";
                break;
            case "183-0-S":
                result ="South Pointe";
                break;
            case "183-0-R":
                result ="Richmond West";
                break;
            case "185-0-U":
                result ="University of Manitoba";
                break;
            case "185-1-O":
                result ="Osborne Village";
                break;
        }
        return result;
    }
}
