package herbaccara.jusokr.exception

class JusoKrResponseException(val errorCode: String, override val message: String) : RuntimeException(message)
