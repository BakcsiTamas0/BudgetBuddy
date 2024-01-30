package com.example.budgetbuddy.Adapters.ExchangeAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.DataClasses.ExchangeData.ExchangeItem
import com.example.budgetbuddy.R

class ExchangeRecyclerViewAdapter (
    private val context: Context,
    private val itemList: MutableList<ExchangeItem>):
    RecyclerView.Adapter<ExchangeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_exchange_recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExchangeRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val exchangeAbbreviation: TextView = itemView.findViewById(R.id.exchangeAbbreviation)
        private val exchangeFullName: TextView = itemView.findViewById(R.id.exchangeFullName)
        private val exchangeBuyPrice: TextView = itemView.findViewById(R.id.exchangeRate)

        fun bind(item: ExchangeItem) {
            exchangeAbbreviation.text = item.currency
            exchangeBuyPrice.text = item.rate.toString()
            setFullName(ExchangeItem(item.currency, item.rate))
        }

        private fun setFullName(item: ExchangeItem) {
            when (item.currency) {
                "RON" -> {
                    exchangeFullName.text = "Romanian Leu"
                }
                "AED" -> {
                    exchangeFullName.text = "United Arab Emirates Dirham"
                }
                "AFN" -> {
                    exchangeFullName.text = "Afghan Afghani"
                }
                "ALL" -> {
                    exchangeFullName.text = "Albanian Lek"
                }
                "AMD" -> {
                    exchangeFullName.text = "Armenian Dram"
                }
                "ANG" -> {
                    exchangeFullName.text = "Netherlands Antillean Guilder"
                }
                "AOA" -> {
                    exchangeFullName.text = "Angolan Kwanza"
                }
                "ARS" -> {
                    exchangeFullName.text = "Argentine Peso"
                }
                "AUD" -> {
                    exchangeFullName.text = "Australian Dollar"
                }
                "AWG" -> {
                    exchangeFullName.text = "Aruban Florin"
                }
                "AZN" -> {
                    exchangeFullName.text = "Azerbaijani Manat"
                }
                "BAM" -> {
                    exchangeFullName.text = "Bosnia-Herzegovina Convertible Mark"
                }
                "BBD" -> {
                    exchangeFullName.text = "Barbadian Dollar"
                }
                "BDT" -> {
                    exchangeFullName.text = "Bangladeshi Taka"
                }
                "BGN" -> {
                    exchangeFullName.text = "Bulgarian Lev"
                }
                "BHD" -> {
                    exchangeFullName.text = "Bahraini Dinar"
                }
                "BIF" -> {
                    exchangeFullName.text = "Burundian Franc"
                }
                "BMD" -> {
                    exchangeFullName.text = "Bermudian Dollar"
                }
                "BND" -> {
                    exchangeFullName.text = "Brunei Dollar"
                }
                "BOB" -> {
                    exchangeFullName.text = "Bolivian Boliviano"
                }
                "BRL" -> {
                    exchangeFullName.text = "Brazilian Real"
                }
                "BSD" -> {
                    exchangeFullName.text = "Bahamian Dollar"
                }
                "BTN" -> {
                    exchangeFullName.text = "Bhutanese Ngultrum"
                }
                "BWP" -> {
                    exchangeFullName.text = "Botswana Pula"
                }
                "BYN" -> {
                    exchangeFullName.text = "Belarusian Ruble"
                }
                "BZD" -> {
                    exchangeFullName.text = "Belize Dollar"
                }
                "CAD" -> {
                    exchangeFullName.text = "Canadian Dollar"
                }
                "CDF" -> {
                    exchangeFullName.text = "Congolese Franc"
                }
                "CHF" -> {
                    exchangeFullName.text = "Swiss Franc"
                }
                "CLP" -> {
                    exchangeFullName.text = "Chilean Peso"
                }
                "CNY" -> {
                    exchangeFullName.text = "Chinese Yuan"
                }
                "COP" -> {
                    exchangeFullName.text = "Colombian Peso"
                }
                "CRC" -> {
                    exchangeFullName.text = "Costa Rican Colón"
                }
                "CUP" -> {
                    exchangeFullName.text = "Cuban Peso"
                }
                "CVE" -> {
                    exchangeFullName.text = "Cape Verdean Escudo"
                }
                "CZK" -> {
                    exchangeFullName.text = "Czech Republic Koruna"
                }
                "DJF" -> {
                    exchangeFullName.text = "Djiboutian Franc"
                }
                "DKK" -> {
                    exchangeFullName.text = "Danish Krone"
                }
                "DOP" -> {
                    exchangeFullName.text = "Dominican Peso"
                }
                "DZD" -> {
                    exchangeFullName.text = "Algerian Dinar"
                }
                "EGP" -> {
                    exchangeFullName.text = "Egyptian Pound"
                }
                "ERN" -> {
                    exchangeFullName.text = "Eritrean Nakfa"
                }
                "ETB" -> {
                    exchangeFullName.text = "Ethiopian Birr"
                }
                "EUR" -> {
                    exchangeFullName.text = "Euro"
                }
                "FJD" -> {
                    exchangeFullName.text = "Fijian Dollar"
                }
                "FKP" -> {
                    exchangeFullName.text = "Falkland Islands Pound"
                }
                "FOK" -> {
                    exchangeFullName.text = "Faroese Króna"
                }
                "GBP" -> {
                    exchangeFullName.text = "British Pound Sterling"
                }
                "GEL" -> {
                    exchangeFullName.text = "Georgian Lari"
                }
                "GGP" -> {
                    exchangeFullName.text = "Guernsey Pound"
                }
                "GHS" -> {
                    exchangeFullName.text = "Ghanaian Cedi"
                }
                "GIP" -> {
                    exchangeFullName.text = "Gibraltar Pound"
                }
                "GMD" -> {
                    exchangeFullName.text = "Gambian Dalasi"
                }
                "GNF" -> {
                    exchangeFullName.text = "Guinean Franc"
                }
                "GTQ" -> {
                    exchangeFullName.text = "Guatemalan Quetzal"
                }
                "GYD" -> {
                    exchangeFullName.text = "Guyanaese Dollar"
                }
                "HKD" -> {
                    exchangeFullName.text = "Hong Kong Dollar"
                }
                "HNL" -> {
                    exchangeFullName.text = "Honduran Lempira"
                }
                "HRK" -> {
                    exchangeFullName.text = "Croatian Kuna"
                }
                "HTG" -> {
                    exchangeFullName.text = "Haitian Gourde"
                }
                "HUF" -> {
                    exchangeFullName.text = "Hungarian Forint"
                }
                "IDR" -> {
                    exchangeFullName.text = "Indonesian Rupiah"
                }
                "ILS" -> {
                    exchangeFullName.text = "Israeli New Shekel"
                }
                "IMP" -> {
                    exchangeFullName.text = "Isle of Man Pound"
                }
                "INR" -> {
                    exchangeFullName.text = "Indian Rupee"
                }
                "IQD" -> {
                    exchangeFullName.text = "Iraqi Dinar"
                }
                "IRR" -> {
                    exchangeFullName.text = "Iranian Rial"
                }
                "ISK" -> {
                    exchangeFullName.text = "Icelandic Króna"
                }
                "JEP" -> {
                    exchangeFullName.text = "Jersey Pound"
                }
                "JMD" -> {
                    exchangeFullName.text = "Jamaican Dollar"
                }
                "JOD" -> {
                    exchangeFullName.text = "Jordanian Dinar"
                }
                "JPY" -> {
                    exchangeFullName.text = "Japanese Yen"
                }
                "KES" -> {
                    exchangeFullName.text = "Kenyan Shilling"
                }
                "KGS" -> {
                    exchangeFullName.text = "Kyrgyzstani Som"
                }
                "KHR" -> {
                    exchangeFullName.text = "Cambodian Riel"
                }
                "KID" -> {
                    exchangeFullName.text = "Kiribati Dollar"
                }
                "KMF" -> {
                    exchangeFullName.text = "Comorian Franc"
                }
                "KRW" -> {
                    exchangeFullName.text = "South Korean Won"
                }
                "KWD" -> {
                    exchangeFullName.text = "Kuwaiti Dinar"
                }
                "KYD" -> {
                    exchangeFullName.text = "Cayman Islands Dollar"
                }
                "KZT" -> {
                    exchangeFullName.text = "Kazakhstani Tenge"
                }
                "LAK" -> {
                    exchangeFullName.text = "Laotian Kip"
                }
                "LBP" -> {
                    exchangeFullName.text = "Lebanese Pound"
                }
                "LKR" -> {
                    exchangeFullName.text = "Sri Lankan Rupee"
                }
                "LRD" -> {
                    exchangeFullName.text = "Liberian Dollar"
                }
                "LSL" -> {
                    exchangeFullName.text = "Lesotho Loti"
                }
                "LYD" -> {
                    exchangeFullName.text = "Libyan Dinar"
                }
                "MAD" -> {
                    exchangeFullName.text = "Moroccan Dirham"
                }
                "MDL" -> {
                    exchangeFullName.text = "Moldovan Leu"
                }
                "MGA" -> {
                    exchangeFullName.text = "Malagasy Ariary"
                }
                "MKD" -> {
                    exchangeFullName.text = "Macedonian Denar"
                }
                "MMK" -> {
                    exchangeFullName.text = "Myanmar Kyat"
                }
                "MNT" -> {
                    exchangeFullName.text = "Mongolian Tugrik"
                }
                "MOP" -> {
                    exchangeFullName.text = "Macanese Pataca"
                }
                "MRU" -> {
                    exchangeFullName.text = "Mauritanian Ouguiya"
                }
                "MUR" -> {
                    exchangeFullName.text = "Mauritian Rupee"
                }
                "MVR" -> {
                    exchangeFullName.text = "Maldivian Rufiyaa"
                }
                "MWK" -> {
                    exchangeFullName.text = "Malawian Kwacha"
                }
                "MXN" -> {
                    exchangeFullName.text = "Mexican Peso"
                }
                "MYR" -> {
                    exchangeFullName.text = "Malaysian Ringgit"
                }
                "MZN" -> {
                    exchangeFullName.text = "Mozambican Metical"
                }
                "NAD" -> {
                    exchangeFullName.text = "Namibian Dollar"
                }
                "NGN" -> {
                    exchangeFullName.text = "Nigerian Naira"
                }
                "NIO" -> {
                    exchangeFullName.text = "Nicaraguan Córdoba"
                }
                "NOK" -> {
                    exchangeFullName.text = "Norwegian Krone"
                }
                "NPR" -> {
                    exchangeFullName.text = "Nepalese Rupee"
                }
                "NZD" -> {
                    exchangeFullName.text = "New Zealand Dollar"
                }
                "OMR" -> {
                    exchangeFullName.text = "Omani Rial"
                }
                "PAB" -> {
                    exchangeFullName.text = "Panamanian Balboa"
                }
                "PEN" -> {
                    exchangeFullName.text = "Peruvian Nuevo Sol"
                }
                "PGK" -> {
                    exchangeFullName.text = "Papua New Guinean Kina"
                }
                "PHP" -> {
                    exchangeFullName.text = "Philippine Peso"
                }
                "PKR" -> {
                    exchangeFullName.text = "Pakistani Rupee"
                }
                "PLN" -> {
                    exchangeFullName.text = "Polish Złoty"
                }
                "PYG" -> {
                    exchangeFullName.text = "Paraguayan Guarani"
                }
                "QAR" -> {
                    exchangeFullName.text = "Qatari Rial"
                }
                "RSD" -> {
                    exchangeFullName.text = "Serbian Dinar"
                }
                "RUB" -> {
                    exchangeFullName.text = "Russian Ruble"
                }
                "RWF" -> {
                    exchangeFullName.text = "Rwandan Franc"
                }
                "SAR" -> {
                    exchangeFullName.text = "Saudi Riyal"
                }
                "SBD" -> {
                    exchangeFullName.text = "Solomon Islands Dollar"
                }
                "SCR" -> {
                    exchangeFullName.text = "Seychellois Rupee"
                }
                "SDG" -> {
                    exchangeFullName.text = "Sudanese Pound"
                }
                "SEK" -> {
                    exchangeFullName.text = "Swedish Krona"
                }
                "SGD" -> {
                    exchangeFullName.text = "Singapore Dollar"
                }
                "SHP" -> {
                    exchangeFullName.text = "Saint Helena Pound"
                }
                "SLE" -> {
                    exchangeFullName.text = "Sierra Leonean Leone"
                }
                "SLL" -> {
                    exchangeFullName.text = "Sierra Leonean Leone"
                }
                "SOS" -> {
                    exchangeFullName.text = "Somali Shilling"
                }
                "SRD" -> {
                    exchangeFullName.text = "Surinamese Dollar"
                }
                "SSP" -> {
                    exchangeFullName.text = "South Sudanese Pound"
                }
                "STN" -> {
                    exchangeFullName.text = "São Tomé and Príncipe Dobra"
                }
                "SYP" -> {
                    exchangeFullName.text = "Syrian Pound"
                }
                "SZL" -> {
                    exchangeFullName.text = "Swazi Lilangeni"
                }
                "THB" -> {
                    exchangeFullName.text = "Thai Baht"
                }
                "TJS" -> {
                    exchangeFullName.text = "Tajikistani Somoni"
                }
                "TMT" -> {
                    exchangeFullName.text = "Turkmenistani Manat"
                }
                "TND" -> {
                    exchangeFullName.text = "Tunisian Dinar"
                }
                "TOP" -> {
                    exchangeFullName.text = "Tongan Pa'anga"
                }
                "TRY" -> {
                    exchangeFullName.text = "Turkish Lira"
                }
                "TTD" -> {
                    exchangeFullName.text = "Trinidad and Tobago Dollar"
                }
                "TVD" -> {
                    exchangeFullName.text = "Tuvaluan Dollar"
                }
                "TWD" -> {
                    exchangeFullName.text = "New Taiwan Dollar"
                }
                "TZS" -> {
                    exchangeFullName.text = "Tanzanian Shilling"
                }
                "UAH" -> {
                    exchangeFullName.text = "Ukrainian Hryvnia"
                }
                "UGX" -> {
                    exchangeFullName.text = "Ugandan Shilling"
                }
                "USD" -> {
                    exchangeFullName.text = "United States Dollar"
                }
                "UYU" -> {
                    exchangeFullName.text = "Uruguayan Peso"
                }
                "UZS" -> {
                    exchangeFullName.text = "Uzbekistan Som"
                }
                "VES" -> {
                    exchangeFullName.text = "Venezuelan Bolívar"
                }
                "VND" -> {
                    exchangeFullName.text = "Vietnamese Đồng"
                }
                "VUV" -> {
                    exchangeFullName.text = "Vanuatu Vatu"
                }
                "WST" -> {
                    exchangeFullName.text = "Samoan Tala"
                }
                "XAF" -> {
                    exchangeFullName.text = "Central African CFA Franc"
                }
                "XCD" -> {
                    exchangeFullName.text = "East Caribbean Dollar"
                }
                "XDR" -> {
                    exchangeFullName.text = "Special Drawing Rights"
                }
                "XOF" -> {
                    exchangeFullName.text = "West African CFA franc"
                }
                "XPF" -> {
                    exchangeFullName.text = "CFP Franc"
                }
                "YER" -> {
                    exchangeFullName.text = "Yemeni Rial"
                }
                "ZAR" -> {
                    exchangeFullName.text = "South African Rand"
                }
                "ZMW" -> {
                    exchangeFullName.text = "Zambian Kwacha"
                }
                "ZWL" -> {
                    exchangeFullName.text = "Zimbabwean Dollar"
                }
                else -> {
                    exchangeFullName.text = "Unknown Currency"
                }
            }
        }

    }

}