package racetrack

class User {
    String login
    String password
    String role = "user"

    static constraints = {
        login(blank:false, nullable:false, unique:true)
        password(blank:false, password:true)
        role(inList:["admin", "user"])
    }
    
    static transients = ['admin']
    
    String toString(){
        login
    }
    
    boolean isAdmin(){
        role == "admin"
    }
    
    def beforeInsert = {
        password = password.encodeAsSHA()
    }
}
