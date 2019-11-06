package pick.com.app.varient.owner.pojo;

import java.util.List;

public class ss {


    /**
     * status : 1
     * message : Success
     * upload_url : https://in.endivesoftware.com/sites/pick/uploads/
     * data : [{"vehicle_id":"17","user_id":"44","model_id":"1","model_name":"Audi Audi1","seater_id":"1","seater":"1","vehicle_year":"2017","fuel_type":"4","vehicle_type":"1","vehicle_type_name":"Small","vehicle_subtype":"12","vehicle_subtype_name":"Hatchback","vehicle_transmission":"2","vehicle_transmission_title":"manual","vehicle_for":"1","vehicle_charges_one_day":"1200","one_day_kilometer_charge":"0","per_kilometer_charge":"0","insurance_policy":"1551183171XXbaxtertestdocument.pdf","vehicle_image":"1551183171XXbsnllogo.jpg"},{"vehicle_id":"18","user_id":"44","model_id":"2","model_name":"Mercedes MN3","seater_id":"5","seater":"5","vehicle_year":"2016","fuel_type":"4","vehicle_type":"3","vehicle_type_name":"Large","vehicle_subtype":"20","vehicle_subtype_name":"Hatchback","vehicle_transmission":"2","vehicle_transmission_title":"manual","vehicle_for":"1","vehicle_charges_one_day":"1255","one_day_kilometer_charge":"0","per_kilometer_charge":"0","insurance_policy":"1551183385XXphoto.jpg.jpg","vehicle_image":"1551183385XXbsnllogo.jpg"},{"vehicle_id":"19","user_id":"44","model_id":"2","model_name":"Mercedes MN3","seater_id":"2","seater":"2","vehicle_year":"2018","fuel_type":"2","vehicle_type":"3","vehicle_type_name":"Large","vehicle_subtype":"19","vehicle_subtype_name":"SUV","vehicle_transmission":"1","vehicle_transmission_title":"automatic","vehicle_for":"1","vehicle_charges_one_day":"4500","one_day_kilometer_charge":"0","per_kilometer_charge":"0","insurance_policy":"1551183898XXphoto.jpg.jpg","vehicle_image":"1551183898XXbsnllogo.jpg"}]
     */
    public ss ss;

    private String status;
    private String message;
    private String upload_url;
    private List<Data> data;

    public String getStatus() {

        
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUpload_url() {
        return upload_url;
    }

    public void setUpload_url(String upload_url) {
        this.upload_url = upload_url;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        /**
         * vehicle_id : 17
         * user_id : 44
         * model_id : 1
         * model_name : Audi Audi1
         * seater_id : 1
         * seater : 1
         * vehicle_year : 2017
         * fuel_type : 4
         * vehicle_type : 1
         * vehicle_type_name : Small
         * vehicle_subtype : 12
         * vehicle_subtype_name : Hatchback
         * vehicle_transmission : 2
         * vehicle_transmission_title : manual
         * vehicle_for : 1
         * vehicle_charges_one_day : 1200
         * one_day_kilometer_charge : 0
         * per_kilometer_charge : 0
         * insurance_policy : 1551183171XXbaxtertestdocument.pdf
         * vehicle_image : 1551183171XXbsnllogo.jpg
         */

        private String vehicle_id;
        private String user_id;
        private String model_id;
        private String model_name;
        private String seater_id;
        private String seater;
        private String vehicle_year;
        private String fuel_type;
        private String vehicle_type;
        private String vehicle_type_name;
        private String vehicle_subtype;
        private String vehicle_subtype_name;
        private String vehicle_transmission;
        private String vehicle_transmission_title;
        private String vehicle_for;
        private String vehicle_charges_one_day;
        private String one_day_kilometer_charge;
        private String per_kilometer_charge;
        private String insurance_policy;
        private String vehicle_image;

        public String getVehicle_id() {
            return vehicle_id;
        }

        public void setVehicle_id(String vehicle_id) {
            this.vehicle_id = vehicle_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getModel_id() {
            return model_id;
        }

        public void setModel_id(String model_id) {
            this.model_id = model_id;
        }

        public String getModel_name() {
            return model_name;
        }

        public void setModel_name(String model_name) {
            this.model_name = model_name;
        }

        public String getSeater_id() {
            return seater_id;
        }

        public void setSeater_id(String seater_id) {
            this.seater_id = seater_id;
        }

        public String getSeater() {
            return seater;
        }

        public void setSeater(String seater) {
            this.seater = seater;
        }

        public String getVehicle_year() {
            return vehicle_year;
        }

        public void setVehicle_year(String vehicle_year) {
            this.vehicle_year = vehicle_year;
        }

        public String getFuel_type() {
            return fuel_type;
        }

        public void setFuel_type(String fuel_type) {
            this.fuel_type = fuel_type;
        }

        public String getVehicle_type() {
            return vehicle_type;
        }

        public void setVehicle_type(String vehicle_type) {
            this.vehicle_type = vehicle_type;
        }

        public String getVehicle_type_name() {
            return vehicle_type_name;
        }

        public void setVehicle_type_name(String vehicle_type_name) {
            this.vehicle_type_name = vehicle_type_name;
        }

        public String getVehicle_subtype() {
            return vehicle_subtype;
        }

        public void setVehicle_subtype(String vehicle_subtype) {
            this.vehicle_subtype = vehicle_subtype;
        }

        public String getVehicle_subtype_name() {
            return vehicle_subtype_name;
        }

        public void setVehicle_subtype_name(String vehicle_subtype_name) {
            this.vehicle_subtype_name = vehicle_subtype_name;
        }

        public String getVehicle_transmission() {
            return vehicle_transmission;
        }

        public void setVehicle_transmission(String vehicle_transmission) {
            this.vehicle_transmission = vehicle_transmission;
        }

        public String getVehicle_transmission_title() {
            return vehicle_transmission_title;
        }

        public void setVehicle_transmission_title(String vehicle_transmission_title) {
            this.vehicle_transmission_title = vehicle_transmission_title;
        }

        public String getVehicle_for() {
            return vehicle_for;
        }

        public void setVehicle_for(String vehicle_for) {
            this.vehicle_for = vehicle_for;
        }

        public String getVehicle_charges_one_day() {
            return vehicle_charges_one_day;
        }

        public void setVehicle_charges_one_day(String vehicle_charges_one_day) {
            this.vehicle_charges_one_day = vehicle_charges_one_day;
        }

        public String getOne_day_kilometer_charge() {
            return one_day_kilometer_charge;
        }

        public void setOne_day_kilometer_charge(String one_day_kilometer_charge) {
            this.one_day_kilometer_charge = one_day_kilometer_charge;
        }

        public String getPer_kilometer_charge() {
            return per_kilometer_charge;
        }

        public void setPer_kilometer_charge(String per_kilometer_charge) {
            this.per_kilometer_charge = per_kilometer_charge;
        }

        public String getInsurance_policy() {
            return insurance_policy;
        }

        public void setInsurance_policy(String insurance_policy) {
            this.insurance_policy = insurance_policy;
        }

        public String getVehicle_image() {
            return vehicle_image;
        }

        public void setVehicle_image(String vehicle_image) {
            this.vehicle_image = vehicle_image;
        }
    }
}
