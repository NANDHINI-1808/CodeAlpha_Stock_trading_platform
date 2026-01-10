import java.util.*;

/* MAIN CLASS (must match Programiz run class) */
public class Stock {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Market Data
        Map<String, StockItem> market = new HashMap<>();
        market.put("AAPL", new StockItem("AAPL", 150));
        market.put("GOOGL", new StockItem("GOOGL", 2800));
        market.put("TSLA", new StockItem("TSLA", 700));

        Portfolio portfolio = new Portfolio();

        while (true) {
            System.out.println("\n===== STOCK TRADING PLATFORM =====");
            System.out.println("1. View Market Prices");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n📈 Market Prices:");
                    for (StockItem s : market.values()) {
                        System.out.println(s.getSymbol() + " : ₹" + s.getPrice());
                    }
                    break;

                case 2:
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = sc.next().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int buyQty = sc.nextInt();

                    if (market.containsKey(buySymbol)) {
                        portfolio.buyStock(market.get(buySymbol), buyQty);
                    } else {
                        System.out.println("❌ Stock not found!");
                    }
                    break;

                case 3:
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = sc.next().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int sellQty = sc.nextInt();

                    if (market.containsKey(sellSymbol)) {
                        portfolio.sellStock(market.get(sellSymbol), sellQty);
                    } else {
                        System.out.println("❌ Stock not found!");
                    }
                    break;

                case 4:
                    portfolio.showPortfolio(market);
                    break;

                case 5:
                    System.out.println("👋 Thank you for trading!");
                    sc.close();
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}

/* Stock item class */
class StockItem {
    private String symbol;
    private double price;

    public StockItem(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }
}

/* Portfolio class */
class Portfolio {
    private Map<String, Integer> holdings = new HashMap<>();
    private double balance = 10000;

    public void buyStock(StockItem stock, int qty) {
        double cost = stock.getPrice() * qty;
        if (cost > balance) {
            System.out.println("❌ Insufficient balance!");
            return;
        }
        holdings.put(stock.getSymbol(),
                holdings.getOrDefault(stock.getSymbol(), 0) + qty);
        balance -= cost;
        System.out.println("✅ Stock bought successfully!");
    }

    public void sellStock(StockItem stock, int qty) {
        int owned = holdings.getOrDefault(stock.getSymbol(), 0);
        if (qty > owned) {
            System.out.println("❌ Not enough shares!");
            return;
        }
        holdings.put(stock.getSymbol(), owned - qty);
        balance += stock.getPrice() * qty;
        System.out.println("✅ Stock sold successfully!");
    }

    public void showPortfolio(Map<String, StockItem> market) {
        System.out.println("\n📊 Portfolio:");
        System.out.println("Balance: ₹" + balance);
        for (String s : holdings.keySet()) {
            int q = holdings.get(s);
            if (q > 0) {
                System.out.println(s + " → " + q + " shares | Value: ₹" +
                        (q * market.get(s).getPrice()));
            }
        }
    }
}
