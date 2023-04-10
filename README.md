# CS-180-Project-4

Instructions on how to run:
The Menu class has the main that will be able to have access to all of the classes and files. Download all .txt files and all java files as well. Then you simply run the main.

---------------------------------------------------------------------------------------------------------------------------

Who submitted what:
BrightSpace Report: John Danison
Vocareum: Weston Walker

---------------------------------------------------------------------------------------------------------------------------

Classes Funcationalities:
Menu:
To carry out all the functions required by the project description. Uses many methods to effecitvely combine functionalities of all the classes. Tested throuhg many lots of tries and fails. Menu is effectivly the brain of the code and controls the timing of everything.


Sellers:
This creates a specific instance of a seller so that the user can have a set list of methods to get various details about them. Used all throughout the menu class methods to get the specific information needed. This was tested via try and fail methods. There wasn't a whole lot to test as this is like a median between two classes.

  
Customers:
This creates a specific instance of a customer so that the user can have a set list of methods to get various details about them. Used all throughout the menu class methods to get the specific information needed. This was tested via try and fail methods. There wasn't a whole lot to test as this is like a median between two classes. This is very close to seller's idea except the customer also has a purhcase history and shopping cart.

Products:
This gives all of the product specific information such as name, type, who sellers it, price, and more. Also used throughout the program to help in the aiding of adding information that would have else been lost. This is also testing via trial and error as there isn't a good way of testing it otherwise.

Users:
This class allows for a password to be got and set and stay as an attribute to the user. This is useful for distinguishing who is who and for allowing access back into accounts. This class is mainly used in the menu in the main function. This was tested via trial and error as there are no real means of testing it.

---------------------------------------------------------------------------------------------------------------------------

All classes with Methods and Descriptions:
Menu - to carry out all the functions required by the project description
Classes:
  Main - initialize everything, begins the program
  setSellers - sets the sellers based on the text file Sellers
  setCustomers - sets the customers based on the text file Customers
  Create - creates any new users
  logIn - allows users to log in
  displayFunctions - begins the display process
  display - displays all the products, without organization
  displayByQuantity - displays the products by quantity
  displayByCost - displays the products by price
  Search - allows users to search for products
  readFile - reads the text files, returns a string with information
  Redo - allows users to redo the login/create functions
  custmerAccount - main function where all customer actions occur
  Allows display, purchasing, shows customer cart etc.
  sellerAccount - main function where all seller actions occur
  writeCustomers - writes the text file with the customer information when the user logs out
  writeSellers - writes the text file with the seller information when the user logs out
  viewCustomerStats - dashboard with information for customers
  displayCusStats - display the information stored for customers
  displayCusHistory - display the purchase history formatted per requirements
  viewSellerStats - dashboard with information for sellers
  readProducts - read the store name and number of products to an array for use
  readPurchaseHistory - read the history of the product to an array for use


Sellers
  addProduct - prompts seller for new product information and adds it to the seller’s store
  deleteProduct - deletes specified product from seller’s store
  deleteOrAddStore  - deletes specified store from seller’s account or adds store to seller’s account depending on input
  modifyProduct - prompts seller for different information on a specified product


Customers
  Customer - two constructor methods for a customer account
  setShoppingCart  - takes an arraylist of products sets it to the field
  getShoppingCart - returns shopping cart
  addToShoppingCart - adds a product to shopping cart
  removeFromShoppingCart - removes a product from shopping cart
  setPurchaseHistory - sets products purchased by customer
  getPurhcaseHistory - returns purchase history
  addToPurhcaseHistory - adds a product to purchase history
  removeFromPurchaseHistory - removes a product from purchase history Products
  
  Product - constructor that makes product
  getName, getPrice, getQuantityAvailable, getNameOfStore, getDescription - each getter returns the respective field
  setName, setPrice, setQuantityAvailable, setNameOfStore, setDescription - each setter sets the respective field with the      input

Store 
  Store - constructor that creates store
  setProducts - sets products to inputted arraylist of products
  getProducts - returns store’s products
  removeProduct - removes individual product from store
  getName - returns name of store
  setName - sets name of store from input
  modifyProduct - modifies product from parameters


Users
  User - two-parameter constructor that initializes username and password fields
  getUsername - returns user’s username
  getPassword - return’s user’s password
  setUsername - sets user’s username
  setPassword - sets user’s password
