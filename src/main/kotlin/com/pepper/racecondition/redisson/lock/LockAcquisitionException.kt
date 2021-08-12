package com.pepper.racecondition.redisson.lock

import java.lang.RuntimeException

class LockAcquisitionException(s: String) : RuntimeException(s)