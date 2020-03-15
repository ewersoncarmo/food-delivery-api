package com.smartcook.fooddeliveryapi.serviceintegrationtest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.smartcook.fooddeliveryapi.util.AbstractConfigurationTest;

@SpringBootTest
@Transactional
public class AbstractTransactionalServiceTest extends AbstractConfigurationTest {

}
