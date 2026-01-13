
CREATE OR REPLACE VIEW v_aktywne_paczkomaty AS
SELECT 
    p.id,
    p.kod,
    p.adres,
    p.status,
    COUNT(s.id) as liczba_skrytek,
    COUNT(CASE WHEN s.status = 'WOLNA' THEN 1 END) as wolne_skrytki,
    COUNT(CASE WHEN s.status = 'ZAJETA' THEN 1 END) as zajete_skrytki
FROM paczkomat p
LEFT JOIN skrytka s ON p.id = s.paczkomat_id
WHERE p.status = 'AKTYWNY'
GROUP BY p.id, p.kod, p.adres, p.status;

CREATE OR REPLACE VIEW v_przesylki_status AS
SELECT 
    pr.id,
    pr.numer,
    pr.opis,
    pr.gabaryt,
    pr.waga,
    sp.kod as status_kod,
    sp.opis as status_opis,
    pn.kod as paczkomat_nadania,
    pd.kod as paczkomat_docelowy,
    (SELECT COUNT(*) FROM historia_statusu hs WHERE hs.przesylka_id = pr.id) as liczba_zmian_statusu
FROM przesylka pr
LEFT JOIN status_przesylki sp ON pr.aktualny_status_id = sp.id
LEFT JOIN paczkomat pn ON pr.paczkomat_nadania_id = pn.id
LEFT JOIN paczkomat pd ON pr.paczkomat_docelowy_id = pd.id;

CREATE OR REPLACE VIEW v_awarie_paczkomatow AS
SELECT 
    a.id,
    a.paczkomat_id,
    p.kod as paczkomat_kod,
    a.opis,
    a.data_zgloszenia,
    a.status_awarii,
    a.wymaga_blokady,
    CASE WHEN b.id IS NOT NULL THEN 'TAK' ELSE 'NIE' END as czy_zablokowany,
    b.data_blokady,
    b.data_odblokowania,
    u.login as zglaszajacy_login
FROM awaria_paczkomatu a
JOIN paczkomat p ON a.paczkomat_id = p.id
LEFT JOIN blokada_paczkomatu b ON a.id = b.awaria_id
LEFT JOIN uzytkownik u ON a.zglaszajacy_id = u.id;

CREATE OR REPLACE VIEW v_zadania_dystrybucyjne AS
SELECT 
    z.id,
    z.typ,
    z.status,
    z.data_startu,
    z.data_konca,
    s.nazwa as sortownia_nazwa,
    u.login as kurier_login,
    u.rola as kurier_rola,
    (SELECT COUNT(*) FROM przesylka_zadanie pz WHERE pz.zadanie_id = z.id) as liczba_przesylek
FROM zadanie_dystrybucyjne z
LEFT JOIN sortownia s ON z.sortownia_id = s.id
LEFT JOIN uzytkownik u ON z.kurier_id = u.id;

CREATE OR REPLACE VIEW v_statystyki_uzytkownikow AS
SELECT 
    u.id,
    u.login,
    u.rola,
    (SELECT COUNT(*) FROM zadanie_dystrybucyjne z WHERE z.kurier_id = u.id) as liczba_zadan,
    (SELECT COUNT(*) FROM awaria_paczkomatu a WHERE a.zglaszajacy_id = u.id) as liczba_zgloszonych_awarii,
    (SELECT COUNT(*) FROM zgloszenie_nieprawidlowej_przesylki z WHERE z.zglaszajacy_id = u.id) as liczba_zgloszen_przesylek
FROM uzytkownik u;

