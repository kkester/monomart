describe("template spec", () => {
  it("passes", () => {
    cy.visit("localhost:8888");
  });
});

describe("View purchase history", () => {
  it("Shopper is on Home Page and navigates to the History", () => {
    cy.visit("localhost:8888");
    cy.findByLabelText("History").click();
  })
})
