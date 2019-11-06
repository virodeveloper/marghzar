package pick.com.app.base.model

class LocationModel{
    constructor(address: String, latitude: String, logitude: String, landmark: String) {
        this.address = address
        this.latitude = latitude
        this.logitude = logitude
        this.landmark = landmark
    }

    constructor(address: String, latitude: String, logitude: String, landmark: String,owner_city: String) {
        this.address = address
        this.latitude = latitude
        this.logitude = logitude
        this.landmark = landmark
        this.owner_city = owner_city
    }

    var address=""
    var latitude=""
    var logitude=""
    var landmark=""
    var owner_city=""
}

