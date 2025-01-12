package com.hongphat.borrowservice.module.factory;

import com.hongphat.borrowservice.command.event.CreateBorrowEvent;
import com.hongphat.borrowservice.command.model.BorrowModel;
import com.hongphat.common_service.common.BaseCrudFactory;

/**
 * IBorrowFactory
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 1 :51 CH 12/01/2025
 */
public interface IBorrowFactory extends BaseCrudFactory<BorrowModel, CreateBorrowEvent> {
}
