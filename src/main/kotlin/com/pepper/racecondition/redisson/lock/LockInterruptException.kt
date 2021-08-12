package com.pepper.racecondition.redisson.lock

import java.lang.Exception

class LockInterruptException(s: String, e: Exception) : Exception(s, e)