package br.com.wellingtoncosta.amdk.domain.model

/**
 * @author Wellington Costa on 30/12/2017.
 */

class UsersResponse {

    var result: Boolean = false
    var message: String? = null
    var users: Users? = null
    var token: String? = null
    var code: Int = 0

    class Users {
        var id: Int = 0
        var first_name: String? = null
        var last_name: String? = null
        var email: String? = null
        var password: String? = null
        var phone_number: String? = null
        var user_type: String? = null
        var address: String? = null
        var latitude: String? = null
        var longitude: String? = null
        var city: String? = null
        var state: String? = null
        var postal_code: String? = null
        var organization_name: String? = null
        var organization_address: String? = null
        var organization_city: String? = null
        var organization_state: String? = null
        var organization_postal_code: String? = null
        var created_at: String? = null
        var updated_at: String? = null
        var driver_status: String? = null
        var avatar: String? = null
    }
}