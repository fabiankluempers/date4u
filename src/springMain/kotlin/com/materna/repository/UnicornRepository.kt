package com.materna.repository

import com.materna.entity.Unicorn
import org.springframework.data.jpa.repository.JpaRepository

interface UnicornRepository : JpaRepository<Unicorn, Long>
