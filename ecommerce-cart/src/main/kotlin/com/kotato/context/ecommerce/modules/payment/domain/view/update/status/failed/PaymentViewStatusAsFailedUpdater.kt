package com.kotato.context.ecommerce.modules.payment.domain.view.update.status.failed

import com.kotato.context.ecommerce.modules.payment.domain.PaymentId
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentView
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentViewNotFound
import com.kotato.context.ecommerce.modules.payment.domain.view.PaymentViewRepository
import com.kotato.shared.transaction.ReadModelTransaction
import javax.inject.Named

@Named
open class PaymentViewStatusAsFailedUpdater(private val repository: PaymentViewRepository) {

    @ReadModelTransaction
    open operator fun invoke(id: PaymentId) {
        repository
                .search(id)
                .also { guardPaymentExists(id, it) }!!
                .updateAsFailed()
                .let(repository::save)
    }

    private fun guardPaymentExists(id: PaymentId, view: PaymentView?) {
        view ?: throw PaymentViewNotFound(id.asString())
    }

}