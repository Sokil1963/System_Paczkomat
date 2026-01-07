-- Dane inicjalne dla systemu paczkomatów

-- Statusy przesyłek wymagane przez przypadki użycia
INSERT INTO status_przesylki (id, kod, opis) VALUES
(1, 'NADANA', 'Przesyłka została nadana przez nadawcę'),
(2, 'W_SORTOWNI', 'Przesyłka znajduje się w sortowni'),
(3, 'OCZEKUJE_NA_KURIERA', 'Przesyłka oczekuje na odbiór przez kuriera'),
(4, 'W_TRANSPORCIE', 'Przesyłka jest w transporcie'),
(5, 'GOTOWA_DO_ODBIORU', 'Przesyłka gotowa do odbioru w paczkomacie'),
(6, 'ODEBRANA', 'Przesyłka została odebrana przez odbiorcę'),
(7, 'USZKODZONA', 'Przesyłka została uszkodzona'),
(8, 'ZAGUBIONA', 'Przesyłka została zagubiona'),
(9, 'PROBLEM', 'Występuje problem z przesyłką'),
(10, 'ZWROT', 'Przesyłka w trakcie zwrotu do nadawcy')
ON CONFLICT (id) DO NOTHING;

-- Domyślny użytkownik admin (hasło: admin123)
INSERT INTO uzytkownik (id, login, haslo_hash, rola) VALUES
(1, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqMgRlE/9V8h.DjWWGPRRPqVo7ZKO', 'ADMIN')
ON CONFLICT (id) DO NOTHING;

-- Testowy kurier (hasło: kurier123)
INSERT INTO uzytkownik (id, login, haslo_hash, rola) VALUES
(2, 'kurier1', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqMgRlE/9V8h.DjWWGPRRPqVo7ZKO', 'KURIER')
ON CONFLICT (id) DO NOTHING;

-- Testowa sortownia
INSERT INTO sortownia (id, nazwa, adres) VALUES
(1, 'Sortownia Centralna', 'ul. Logistyczna 1, 00-001 Warszawa')
ON CONFLICT (id) DO NOTHING;

-- Testowy paczkomat
INSERT INTO paczkomat (id, kod, status, adres, sortownia_id) VALUES
(1, 'WAW001', 'AKTYWNY', 'ul. Testowa 1, 00-001 Warszawa', 1)
ON CONFLICT (id) DO NOTHING;

