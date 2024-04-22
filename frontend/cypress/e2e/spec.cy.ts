describe("template spec", () => {
  it("passes", () => {
    cy.visit("https://example.cypress.io");
  });
});

describe("View purchase history", () => {
  it("Shopper is on Home Page and navigates to the History", () => {
    cy.findByLabelText("History").click();
  })
})
