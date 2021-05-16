package com.adjourtechnologie.reseauenset.security;

public interface SecurityPrams {
    String JWT_HEADER_NAME = "Authorization";
    String SECRET = "xgzhgdefuzezehjfzeoifhezbhfzhgfgezqjbevfhgfajgfdhsbvgfhzegfazejgfhzegfagfghfqsdgfefgqahdfgagfazegazfhuazbgfaz_adjourTechnologie2020@gt";
    int EXPIRATION_TEN_DAY = 864000000; //10jours
    int EXPIRATION_ONE_DAY = 86400000; //1jours
    int EXPIRATION_TREE_DAY = 259200000; //3jours
    String HEADER_PREFIX = "cms_nord_cm ";

    String BASE_URL = "";
}
