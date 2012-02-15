package racetrack

import org.junit.*
import grails.test.mixin.*
import racetrack.SHACodec
import org.codehaus.groovy.grails.plugins.codecs.*

@TestFor(UserController)
@Mock(User)
class UserControllerTests {

    void setUp() {
        String.metaClass.encodeAsBase64 = {->
            Base64Codec.encode(delegate)
        }
        String.metaClass.encodeAsSHA = {->
            SHACodec.encode(delegate)
        }
    }
    
    void testIndex() {
        controller.index()
        assertEquals "/user/list",
                     response.redirectedUrl
    }
    
    void testShow(){
        def jdoe = new User(login:"jdoe")
        def suziq = new User(login:"suziq")
        jdoe.save(flush: true)
        suziq.save(flush: true)
        
        controller.params.id = 2
        def map = controller.show()
        assertNotNull "map can't be null", map
        assertEquals "suziq", map.userInstance.login
    }
    
    void testSuccessfulAuthenticate(){
        def jdoe = new User(login:"jdoe",
                            password:"password".encodeAsSHA())
        jdoe.save(flush: true)
        
        controller.params.login = "jdoe"
        controller.params.password = "password"
        controller.authenticate()
        assertNotNull controller.session.user
        assertEquals "jdoe", controller.session.user.login
    }
    
    void testWrongAuthenticate(){
        def jdoe = new User(login:"jdoe",
                            password:"password".encodeAsSHA())
        jdoe.save(flush: true)
        
        controller.params.login = "jdoe"
        controller.params.password = "foo"
        controller.authenticate()
        assertTrue controller.flash.message.startsWith("Sorry, jdoe")
    }
}
