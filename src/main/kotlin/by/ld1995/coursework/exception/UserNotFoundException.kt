package by.ld1995.coursework.exception


class UserNotFoundException : RuntimeException {
    constructor(username: String) : super("$username not found")
    constructor(id: Long) : super("User with id $id is missing")
}