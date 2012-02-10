package racetrack

import org.junit.*
import grails.test.mixin.*


@TestFor(UserController)
@Mock(User)
class UserControllerTests {

    void testIndex() {
        controller.index()
        assertEquals "/user/list",
                     response.redirectedUrl
    }
}
