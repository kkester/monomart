describe("template spec", () => {
  it("passes", () => {
    cy.visit("");
  });
});

describe("View purchase history", () => {
  it("Shopper is on Home Page and navigates to the History", () => {
    alert(JSON.stringify(Cypress.config(), null, 2))
    cy.visit("");
    cy.findByLabelText(/history/i).click();
  })
})
