package io.sphere.sdk.customers.commands;

import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.CustomerInvalidCredentials;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.customers.CustomerFixtures.PASSWORD;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

public class CustomerChangePasswordCommandTest extends IntegrationTest {

    @Test
    public void execution() throws Exception {
        withCustomer(client(), customer -> {
            final String oldPassword = PASSWORD;
            final String newPassword = "newSecret";
            final Customer updatedCustomer = execute(CustomerChangePasswordCommand.of(customer, oldPassword, newPassword));
            assertThat(customer.getPassword()).isNotEqualTo(updatedCustomer.getPassword());
            final CustomerSignInResult signInResult =
                    execute(CustomerSignInCommand.of(customer.getEmail(), newPassword));
            assertThat(signInResult.getCustomer().hasSameIdAs(updatedCustomer))
                    .overridingErrorMessage("sign in works with new password")
                    .isTrue();
            try {
                execute(CustomerSignInCommand.of(customer.getEmail(), oldPassword, Optional.empty()));
                fail();
            } catch (final ErrorResponseException e) {
                assertThat(e.hasErrorCode(CustomerInvalidCredentials.CODE)).isTrue();
            }
        });
    }

    @Test
    public void executionDemo() throws Exception {
        withCustomer(client(), customer -> {
            final SphereClient client = client().getUnderlying();
            demo(client, customer.getEmail());

            final String oldPassword = PASSWORD;
            final String newPassword = "newSecret";
            final Customer updatedCustomer = execute(CustomerChangePasswordCommand.of(customer, oldPassword, newPassword));
            assertThat(customer.getPassword()).isNotEqualTo(updatedCustomer.getPassword());
            final CustomerSignInResult signInResult =
                    execute(CustomerSignInCommand.of(customer.getEmail(), newPassword));
            assertThat(signInResult.getCustomer().hasSameIdAs(updatedCustomer))
                    .overridingErrorMessage("sign in works with new password")
                    .isTrue();
            try {
                execute(CustomerSignInCommand.of(customer.getEmail(), oldPassword, Optional.empty()));
                fail();
            } catch (final ErrorResponseException e) {
                assertThat(e.hasErrorCode(CustomerInvalidCredentials.CODE)).isTrue();
            }
        });
    }

    private void demo(final SphereClient client, final String email) {
        final String wrongPassword = "wrong password";
        final CustomerSignInCommand signInCommand = CustomerSignInCommand.of(email, wrongPassword);
        final CompletionStage<CustomerSignInResult> future = client.execute(signInCommand);
        future.whenCompleteAsync((signInResult, exception) -> {
            if (signInResult != null) {
                println("Signing worked");
            } else if (exception instanceof ErrorResponseException) {
                final ErrorResponseException errorResponseException = (ErrorResponseException) exception;
                final String code = CustomerInvalidCredentials.CODE;
                if (errorResponseException.hasErrorCode(code)) {
                    println("customer has invalid credentials");
                }
            }
        });
    }

    private void println(final String s) {
        //ignore, is for demo
    }
}