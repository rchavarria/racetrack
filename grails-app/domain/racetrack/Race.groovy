package racetrack

class Race {
    String name
    Date startDate
    String city
    String state
    BigDecimal distance
    BigDecimal cost
    Integer maxRunners = 100000

    static constraints = {
        name(blank:false, maxSize:50)
        startDate()
        city()
        state(inList:["GA", "NC", "SC", "VA"])
        distance(min:0.0)
        cost(min:0.0, max:100.0)
        maxRunners(min:0, max:100000)
    }

    // mapping class names to alternate table names and field names to alternate column names
    // O tb se puede usar para otras 'cosillas'
    // see: http://grails.org/GORM+-+Mapping+DSL
    static mapping = {
        sort "startDate" // lista objetos Race ordenados por este campo
    }
}
