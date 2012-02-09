package racetrack

import grails.test.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
class UserTests extends GrailsUnitTestCase {

    void testSimpleConstraints() {
        def user = new User(login:"someone",
                            password:"blah",
                            role:"SuperUser")
        
        // oops—role should be either 'admin' or 'user'
        // will the validation pick that up?
        assertFalse user.validate()
    }
}
