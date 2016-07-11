/*
 * (C) ROYAL PHILIPS N.V., 2016
 * All rights are reserved. Reproduction in whole or in part is prohibited without the prior
 * written consent of the copyright holder. This source code and any compilation or derivative
 * thereof is the proprietary information of ROYAL PHILIPS N.V. and is confidential in nature.
 * Under no circumstances is this software to be combined with any Open Source Software in any
 * way or placed under an Open Source License of any type without the express written permission
 * of ROYAL PHILIPS N.V.
 */

package org.hetzijzo.strucdocs.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class StrucdocsEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrucdocsEurekaApplication.class, args);
	}
}
