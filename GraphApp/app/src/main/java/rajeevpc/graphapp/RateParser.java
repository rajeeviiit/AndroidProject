package rajeevpc.graphapp;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RateParser {
    //{"bpi":
    // {"2013-09-01":128.2597,
    // "2013-09-02":127.3648,
    // "2013-09-03":127.5915,
    // "2013-09-04":120.5738,
    // "2013-09-05":120.5333},
    // "disclaimer":"This data was produced from the CoinDesk Bitcoin Price Index. BPI value data returned as USD.",
    // "time":{"updated":"Sep 6, 2013 00:03:00 UTC","updatedISO":"2013-09-06T00:03:00+00:00"}}

    private String startDate;
    private String endDate;
    public RateParser setDateRange(String startDate,String endDate){
        this.startDate = startDate;
        this.endDate=endDate;
        return this;
    }

    private ArrayList<String> getDates() throws ParseException {
        ArrayList<String> dates = new ArrayList<>();

        String str_date = "2013-09-01";
        String end_date = "2013-09-05";

        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(str_date);
        Date endDate = formatter.parse(end_date);
        long interval = 24*1000*60*60;
        long endTime = endDate.getTime();
        long currTime = startDate.getTime();
        while(currTime<=endTime){
            dates.add(formatter.format(new Date(currTime)));
            currTime+=interval;
        }
        return dates;
    }

    public ArrayList<Dataa> getRates(String response) throws JSONException, ParseException {
        ArrayList<Dataa> datas = new ArrayList<>();

        JSONObject object = new JSONObject(response);
        JSONObject bpiObject = object.getJSONObject("bpi");
        ArrayList<String> datesKeys = getDates();

        for(int i=0;i<datesKeys.size();i++){
            datas.add(new Dataa(datesKeys.get(i).toString(),bpiObject.getDouble(datesKeys.get(i))));
        }

        return datas;
    }

    class Dataa{
        public String date;
        public double rate;

        public Dataa(String date, double rate) {
            this.date = date;
            this.rate = rate;
        }
    }
}