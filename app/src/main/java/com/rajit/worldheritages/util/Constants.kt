package com.rajit.worldheritages.util

import android.net.Uri
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Constants {

    const val DB_NAME = "WorldHeritageDB"
    const val TABLE_NAME = "HeritageTable"
    const val FAVOURITE_TABLE_NAME = "FavouritesTable"
    const val PAGE_SIZE = 20

    const val USER_FILTER_PREFERENCES_NAME = "UserFilterPreferences"
    const val USER_COUNTRY_PREFERENCE_KEY = "CountryPref"
    const val USER_TAG_PREFERENCE_KEY = "TagPref"

    const val DEFAULT_COUNTRY_FILTER = "ALL"
    const val DEFAULT_TAG_FILTER = "All"

    val countryList = listOf<String>(
        "ALL",
        "AFG",
        "ALB",
        "AND",
        "ARG",
        "ARM",
        "AUS",
        "AUT",
        "AZE",
        "BHR",
        "BGD",
        "BRB",
        "BLR",
        "BEL",
        "BLZ",
        "BEN",
        "IND",
        "BOL",
        "BIH",
        "BWA",
        "BRA",
        "BGR",
        "BFA",
        "CPV",
        "KHM",
        "CMR",
        "CAN",
        "CAF",
        "TCD",
        "CHL",
        "CHN",
        "COL",
        "COG",
        "CRI",
        "CIV",
        "HRV",
        "CUB",
        "CYP",
        "CZE",
        "COD",
        "DNK",
        "DOM",
        "ECU",
        "EGY",
        "SLV",
        "EST",
        "ETH",
        "FJI",
        "FIN",
        "FRA",
        "GAB",
        "GEO",
        "DEU",
        "GHA",
        "GRC",
        "GIN",
        "HTI",
        "HND",
        "HUN",
        "ISL",
        "IDN",
        "IRN",
        "IRQ",
        "IRL",
        "ISR",
        "ITA",
        "JAM",
        "JPN",
        "JOR",
        "KAZ",
        "KEN",
        "KIR",
        "KGZ",
        "LAO",
        "LVA",
        "LBN",
        "LSO",
        "LBY",
        "LTU",
        "LUX",
        "MDG",
        "MWI",
        "MYS",
        "MLI",
        "MLT",
        "MHL",
        "MRT",
        "MUS",
        "MEX",
        "MDA",
        "MNG",
        "MNE",
        "MAR",
        "MOZ",
        "MMR",
        "NAM",
        "NPL",
        "NLD",
        "NZL",
        "NIC",
        "NER",
        "NGA",
        "PRK",
        "MKD",
        "NOR",
        "OMN",
        "PAK",
        "PLW",
        "PAN",
        "PNG",
        "PRY",
        "PER",
        "PHL",
        "POL",
        "PRT",
        "QAT",
        "ROU",
        "RUS",
        "KNA",
        "LCA",
        "SMR",
        "SAU",
        "SEN",
        "SRB",
        "SYC",
        "SGP",
        "SVK",
        "SVN",
        "SLB",
        "ZAF",
        "KOR",
        "ESP",
        "LKA",
        "PSE",
        "SDN",
        "SUR",
        "SWE",
        "CHE",
        "SYR",
        "TJK",
        "TZA",
        "THA",
        "TGO",
        "TUN",
        "TUR",
        "TKM",
        "UGA",
        "UKR",
        "ARE",
        "GBR",
        "USA",
        "URY",
        "UZB",
        "VUT",
        "VAT",
        "VEN",
        "VNM",
        "YEM",
        "ZMB",
        "ZWE"
    )

    val tagList = listOf<String>(
        "All",
        "Cultural",
        "Mixed",
        "Natural"
    )

    // Convert Country Abbreviation to Country Full Name
    fun convertCountryShortToFullText(abbreviation: String): String {
        return when (abbreviation) {
            "ALL" -> "All"
            "CMR" -> "Cameroon"
            "NAM" -> "Namibia"
            "ALB" -> "Albania"
            "IRL" -> "Ireland"
            "LKA" -> "Sri Lanka"
            "ROU" -> "Romania"
            "ITA" -> "Italy"
            "BOL" -> "Bolivia"
            "TGO" -> "Togo"
            "SAU" -> "Saudi Arabia"
            "BGR" -> "Bulgaria"
            "ESP" -> "Spain"
            "QAT" -> "Qatar"
            "MNG" -> "Mongolia"
            "VEN" -> "Venezuela"
            "NLD" -> "Netherlands"
            "PHL" -> "Philippines"
            "DNK" -> "Denmark"
            "KOR" -> "South Korea"
            "UKR" -> "Ukraine"
            "OMN" -> "Oman"
            "PER" -> "Peru"
            "CZE" -> "Czechia"
            "USA" -> "United States of America"
            "HND" -> "Honduras"
            "PSE" -> "State of Palestine"
            "BEN" -> "Benin"
            "CHE" -> "Switzerland"
            "SLB" -> "Solomon Islands"
            "AFG" -> "Afghanistan"
            "JOR" -> "Jordan"
            "SWE" -> "Sweden"
            "MDA" -> "Moldova"
            "ARM" -> "Armenia"
            "KGZ" -> "Kyrgyzstan"
            "UZB" -> "Uzbekistan"
            "HTI" -> "Haiti"
            "NER" -> "Niger"
            "BWA" -> "Botswana"
            "YEM" -> "Yemen"
            "PRT" -> "Portugal"
            "GIN" -> "Guinea"
            "DEU" -> "Germany"
            "MEX" -> "Mexico"
            "KHM" -> "Cambodia"
            "LSO" -> "Lesotho"
            "PAN" -> "Panama"
            "MNE" -> "Montenegro"
            "UGA" -> "Uganda"
            "PLW" -> "Palau"
            "ECU" -> "Ecuador"
            "GEO" -> "Georgia"
            "SGP" -> "Singapore"
            "NOR" -> "Norway"
            "ZWE" -> "Zimbabwe"
            "IRQ" -> "Iraq"
            "PRY" -> "Paraguay"
            "DZA" -> "Algeria"
            "SRB" -> "Serbia"
            "MRT" -> "Mauritania"
            "IRN" -> "Iran"
            "FJI" -> "Fiji"
            "ARE" -> "United Arab Emirates"
            "PNG" -> "Papua New Guinea"
            "ETH" -> "Ethiopia"
            "NPL" -> "Nepal"
            "MLT" -> "Malta"
            "AND" -> "Andorra"
            "PRK" -> "North Korea"
            "FRA" -> "France"
            "MYS" -> "Malaysia"
            "AUT" -> "Austria"
            "LCA" -> "Saint Lucia"
            "AUS" -> "Australia"
            "MLI" -> "Mali"
            "MUS" -> "Mauritius"
            "SUR" -> "Suriname"
            "MOZ" -> "Mozambique"
            "ARG" -> "Argentina"
            "SVN" -> "Slovenia"
            "GAB" -> "Gabon"
            "FIN" -> "Finland"
            "MDG" -> "Madagascar"
            "CHN" -> "China"
            "KNA" -> "Saint Kitts and Nevis"
            "VUT" -> "Vanuatu"
            "BRB" -> "Barbados"
            "LBN" -> "Lebanon"
            "PAK" -> "Pakistan"
            "TCD" -> "Chad"
            "BLZ" -> "Belize"
            "KIR" -> "Kiribati"
            "BGD" -> "Bangladesh"
            "BRA" -> "Brazil"
            "TZA" -> "Tanzania"
            "IDN" -> "Indonesia"
            "MKD" -> "North Macedonia"
            "COG" -> "Congo"
            "TUN" -> "Tunisia"
            "POL" -> "Poland"
            "LAO" -> "Laos"
            "LUX" -> "Luxembourg"
            "SLV" -> "El Salvador"
            "LVA" -> "Latvia"
            "EGY" -> "Egypt"
            "GMB" -> "Gambia"
            "MHL" -> "Marshall Islands"
            "NZL" -> "New Zealand"
            "TUR" -> "Turkey"
            "DOM" -> "Dominican Republic"
            "GHA" -> "Ghana"
            "VAT" -> "Vatican City"
            "BIH" -> "Bosnia and Herzegovina"
            "CUB" -> "Cuba"
            "IND" -> "Bharat (India)"
            "ZAF" -> "South Africa"
            "THA" -> "Thailand"
            "COD" -> "Democratic Republic of Congo"
            "LTU" -> "Lithuania"
            "KEN" -> "Kenya"
            "EST" -> "Estonia"
            "URY" -> "Uruguay"
            "SEN" -> "Senegal"
            "CYP" -> "Cyprus"
            "CAF" -> "Central African Republic"
            "TJK" -> "Tajikistan"
            "MWI" -> "Malawi"
            "LBY" -> "Libya"
            "JAM" -> "Jamaica"
            "JPN" -> "Japan"
            "KAZ" -> "Kazakhstan"
            "MAR" -> "Morocco"
            "SYR" -> "Syria"
            "BHR" -> "Bahrain"
            "DMA" -> "Dominica"
            "VNM" -> "Vietnam"
            "NIC" -> "Nicaragua"
            "BLR" -> "Belarus"
            "ZMB" -> "Zambia"
            "COL" -> "Colombia"
            "RUS" -> "Russia"
            "ISL" -> "Iceland"
            "BFA" -> "Burkina Faso"
            "CHL" -> "Chile"
            "AZE" -> "Azerbaijan"
            "ISR" -> "Israel"
            "CAN" -> "Canada"
            "SMR" -> "San Marino"
            "NGA" -> "Nigeria"
            "GBR" -> "United Kingdom"
            "GRC" -> "Greece"
            "HUN" -> "Hungary"
            "MMR" -> "Myanmar"
            "CPV" -> "Cabo Verde"
            "SVK" -> "Slovakia"
            "GTM" -> "Paraguay"
            "SDN" -> "Sudan"
            "TKM" -> "Turkmenistan"
            "CIV" -> "Côte d'Ivoire"
            "HRV" -> "Croatia"
            "BEL" -> "Belgium"
            "CRI" -> "Costa Rica"
            "SYC" -> "Seychelles"
            else -> "Unknown"
        }
    }

    // Compose-Navigation can't directly use URLs in its navigation arguments
    // So, we encode the URL and then pass it as navArgument
    // Decoding of this encoded URL is taken care of by Compose-Navigation
    fun encodeURLForNavigation(url: String): String {
        return URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
    }

    // Compose-Navigation can't directly use Multiline String in its navigation arguments
    // So, we encode the Multiline String and then pass it as navArgument
    // Decoding of this encoded Multiline String is taken care of by Compose-Navigation
    fun encodeMultiLineString(multilineString: String): String {
        return URLEncoder.encode(multilineString, StandardCharsets.UTF_8.toString())
    }

    // Compose-Navigation is decoding Multiline String abnormally
    // So we are doing it ourselves
    fun decodeMultilineString(multilineString: String): String {
        return URLDecoder.decode(multilineString, StandardCharsets.UTF_8.toString())
    }

    // This converts the supplied Latitude and Longitude values to Geo URI
    // Which is used by Google Maps to show the location
    fun convertLatLongToMapURI(lat: Double, long: Double, label: String): Uri {
        val geoURI = "geo:$lat,$long?q=$lat,$long($label)&z=1"
        return Uri.parse(geoURI)
    }

}