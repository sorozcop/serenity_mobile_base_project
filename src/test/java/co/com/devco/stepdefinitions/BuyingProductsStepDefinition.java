package co.com.devco.stepdefinitions;

import co.com.devco.exceptions.PurchaseIsNotCompleteException;
import co.com.devco.questions.Purchase;
import co.com.devco.tasks.AddProduct;
import co.com.devco.tasks.AddProducts;
import co.com.devco.tasks.Login;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.ensure.Ensure;

import java.util.List;
import java.util.Map;

import static co.com.devco.exceptions.PurchaseIsNotCompleteException.PURCHASE_FAILED_MESSAGE_EXCEPTION;
import static co.com.devco.tasks.Checkout.checkout;
import static co.com.devco.userinterface.CheckoutCompletadoPage.LBL_CHECKOUT_COMPLETE;
import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.actors.OnStage.withCurrentActor;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getProxiedDriver;

public class BuyingProductsStepDefinition {

    @Given("that (.*) is login on SwagLabs app")
    public void actorWantsBuyShirts(String actor) {
        OnStage.setTheStage(new OnlineCast());
        theActorCalled(actor).whoCan(BrowseTheWeb.with(getProxiedDriver())).entersTheScene();
        theActorInTheSpotlight().attemptsTo(Login.atSwagLabs());
    }

    @When("He puts in the cart the (.*)")
    public void actorPutShirtCart(String nameProduct) {
        theActorInTheSpotlight().attemptsTo(AddProduct.toTheCart(nameProduct));
    }

    @When("He puts in the cart these products")
    public void actorPutShirtCart(List<Map<String, String>> products) {
        theActorInTheSpotlight().attemptsTo(AddProducts.toCart(products));
    }

    @And("He does the checkout of his purchase")
    public void checkoutPurchase() {
        theActorInTheSpotlight().attemptsTo(checkout());
    }

    @Then("He should see that his purchase is successful")
    public void actorShouldSeePurchase() {
        theActorInTheSpotlight().should(eventually(seeThat(Purchase.isSuccesful())).orComplainWith(PurchaseIsNotCompleteException.class, PURCHASE_FAILED_MESSAGE_EXCEPTION));
    }

    @Then("He should see the message (.*)")
    public void actorShouldSeeCheckout(String confirmationMessage) {
        withCurrentActor(Ensure.that(LBL_CHECKOUT_COMPLETE).text().contains(confirmationMessage));
    }
}