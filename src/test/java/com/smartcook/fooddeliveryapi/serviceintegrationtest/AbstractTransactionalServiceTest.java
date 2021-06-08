package com.smartcook.fooddeliveryapi.serviceintegrationtest;

import com.smartcook.fooddeliveryapi.util.AbstractConfigurationTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class AbstractTransactionalServiceTest extends AbstractConfigurationTest {

}
