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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping
    @ResponseBody
    public String getHello() {
        return "Hello!";
    }
}
