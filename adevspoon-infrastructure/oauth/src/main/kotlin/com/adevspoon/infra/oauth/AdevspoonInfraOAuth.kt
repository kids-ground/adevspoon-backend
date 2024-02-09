package com.adevspoon.infra.oauth

import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients(basePackageClasses = [AdevspoonInfraOAuth::class])
interface AdevspoonInfraOAuth { }