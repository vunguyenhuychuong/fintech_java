<img width="1479" height="579" alt="image" src="https://github.com/user-attachments/assets/a80c8068-0f30-4b97-8351-797cb55fd1d1" /># fintech_java
technical test
Login H2 Console: <img width="1195" height="806" alt="image" src="https://github.com/user-attachments/assets/62884c04-7733-4849-bef2-835eb6f8a3af" />

TASK 1 — Verify Price Aggregation (Scheduler)
-<img width="1903" height="626" alt="image" src="https://github.com/user-attachments/assets/337b4fae-7153-4f18-96b6-641bd4f73fa5" />
-<img width="1685" height="937" alt="image" src="https://github.com/user-attachments/assets/4ab95450-c6ba-4beb-a453-23d73353a41b" />

TASK 2 - Run in H2 console:
- SELECT * FROM AGGREGATED_PRICE ORDER BY UPDATED_AT DESC;
- <img width="1474" height="934" alt="image" src="https://github.com/user-attachments/assets/586713dd-10d3-4b28-9357-391ec56c84de" />

TASK 3 - User can trade based on latest best aggregated price
- SELECT * FROM AGGREGATED_PRICE ORDER BY UPDATED_AT DESC;
- <img width="1184" height="969" alt="image" src="https://github.com/user-attachments/assets/2eb6a3cd-7d78-4aef-8664-be324e7ce8dc" />
- Call POST http://localhost:8080/api/trades
- Body: {
  "symbol": "BTCUSDT",
  "side": "BUY",
  "quantity": 0.1
}
- <img width="1392" height="750" alt="image" src="https://github.com/user-attachments/assets/6fe0a891-4b36-499d-a5aa-136ebb75e1ea" />
- Verify Wallet Changed: <img width="1479" height="579" alt="image" src="https://github.com/user-attachments/assets/14743ff3-7f7a-4a43-9320-85b806c2bc2e" />
- Verify Trade History: <img width="1332" height="579" alt="image" src="https://github.com/user-attachments/assets/7c6253fd-6413-4673-8260-1b342539130c" />

TASK 4 — “Retrieve the user’s crypto currencies wallet balance”
- Same with Step 3 call API
- SELECT * FROM USER_WALLET;
- Verify via API: <img width="851" height="743" alt="image" src="https://github.com/user-attachments/assets/44c81f64-8b56-43ec-9d94-2e8a7192e195" />




